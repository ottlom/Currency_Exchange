package controller;

import jdbc.Jdbc;
import jdbc.SqlUtil;
import model.Currency;
import model.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeController {
    Connection connection = Jdbc.getJdbc().getConnection();
    SqlUtil sqlUtil = new SqlUtil();
    ResultSet resultSet;

    public List<Currency> getAllCurrency() {
        List<Currency> currencyList = new ArrayList<>();
        try {
            resultSet = getPrepareStatement(connection, "SELECT * FROM currencies").executeQuery();
            while (resultSet.next()) {
                currencyList.add(new Currency(
                        resultSet.getString("full_name"),
                        resultSet.getString("code"),
                        resultSet.getString("sign")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyList;
    }

    public Currency getCurrency(String currencyCode) {
        Currency currency = new Currency();
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection, "SELECT * FROM currencies WHERE code=?");
            preparedStatement.setString(1, currencyCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currency.setName(resultSet.getString("full_name"));
                currency.setCode(resultSet.getString("code"));
                currency.setSign(resultSet.getString("sign"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }

    public void saveCurrency(Currency currency) {
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "INSERT INTO currencies (full_name, code, sign) VALUES (?,?,?)");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Currency updateCurrency(Currency currency) {
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "UPDATE currencies SET full_name=?, code=?, sign=? WHERE code=?");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.setString(4, currency.getCode());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Currency(currency.getName(), currency.getCode(), currency.getSign());
    }

    public void delete(String currencyCode) {
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection, "DELETE FROM currencies WHERE code=?");
            preparedStatement.setString(1, currencyCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExchangeRate> getAllRate() {
        List<ExchangeRate> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "SELECT * FROM exchange_rates e " +
                            "LEFT JOIN currencies c_base ON e.base_currency_id = c_base.id " +
                            "LEFT JOIN currencies c_target ON e.target_currency_id = c_target.id");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new ExchangeRate(
                        resultSet.getInt("base_currency_id"),
                        resultSet.getInt("target_currency_id"),
                        resultSet.getInt("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveRate(String baseCurrenciesCode, String targetCurrenciesCode, double rate) {
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate) " +
                            "VALUES ((SELECT id FROM currencies WHERE code=?), (SELECT id FROM currencies WHERE code=?), ?)");
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            preparedStatement.setDouble(3, rate);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ExchangeRate getExchangeRate(String baseCurrenciesCode, String targetCurrenciesCode) {
        ExchangeRate exchangeRate = null;
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "SELECT * FROM exchange_rates " +
                            "WHERE base_currency_id = (SELECT id FROM currencies WHERE code=?) " +
                            "AND target_currency_id = (SELECT id FROM currencies WHERE code=?)");
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exchangeRate = new ExchangeRate(
                        resultSet.getInt("base_currency_id"),
                        resultSet.getInt("target_currency_id"),
                        resultSet.getFloat("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }

    public void updateExchangeRate(String baseCurrenciesCode, String targetCurrenciesCode, double rate) {
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "UPDATE exchange_rates " +
                            "SET rate = ? " +
                            "WHERE base_currency_id = (SELECT id FROM currencies WHERE code=?) " +
                            "AND target_currency_id = (SELECT id FROM currencies WHERE code=?)");
            preparedStatement.setDouble(1, rate);
            preparedStatement.setString(2, baseCurrenciesCode);
            preparedStatement.setString(3, targetCurrenciesCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float changeCurrency(String baseCurrenciesCode, String targetCurrenciesCode, int amount) {
        ExchangeRate rate = getExchangeRate(baseCurrenciesCode, targetCurrenciesCode);
        float convertedAmount;
        if (rate != null) {
            convertedAmount = rate.getRate() * amount;
        } else {
            rate = getExchangeRate(targetCurrenciesCode, baseCurrenciesCode);
            if (rate != null) {
                convertedAmount = amount / rate.getRate();
            } else {
                convertedAmount = changeWithoutStraightRate(baseCurrenciesCode, targetCurrenciesCode, amount);
            }
        }
        return convertedAmount;
    }

    private float changeWithoutStraightRate(String baseCurrenciesCode, String targetCurrenciesCode, int amount) {
        List<Float> result = new ArrayList<>(2);
        try {
            PreparedStatement preparedStatement = getPrepareStatement(connection,
                    "SELECT rate FROM exchange_rates " +
                            "WHERE base_currency_id = (SELECT id FROM currencies WHERE code='USD') AND target_currency_id = (SELECT id FROM currencies WHERE code=?) " +
                            "OR base_currency_id = (SELECT id FROM currencies WHERE code='USD') AND target_currency_id = (SELECT id FROM currencies WHERE code=?)");
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getFloat("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.get(1) / result.get(0) * amount;
    }

    private PreparedStatement getPrepareStatement(Connection connection, String query) {
        return sqlUtil.executeSqlQuery(connection, query);
    }
}