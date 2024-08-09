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
            resultSet = getResultSet(connection, "SELECT * FROM currencies").executeQuery();
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

    public Currency get(String currencyCode) {
        Currency currency = new Currency();
        try {
            PreparedStatement preparedStatement = getResultSet(connection, "SELECT * FROM currencies WHERE code=?");
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

    public Currency save(Currency currency) {
        try {
            PreparedStatement preparedStatement = getResultSet(connection,
                    "INSERT INTO currencies (full_name, code, sign) VALUES (?,?,?)");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }

    public Currency update(Currency currency) {
        try {
            PreparedStatement preparedStatement = getResultSet(connection,
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
            PreparedStatement preparedStatement = getResultSet(connection, "DELETE FROM currencies WHERE code=?");
            preparedStatement.setString(1, currencyCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExchangeRate> getAllRate() {
        List<ExchangeRate> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getResultSet(connection,
                    "SELECT * FROM exchange_rates");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new ExchangeRate(
                        resultSet.getInt("base_currency_id"),
                        resultSet.getInt("target_currency_id"),
                        resultSet.getDouble("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private PreparedStatement getResultSet(Connection connection, String query) {
        return sqlUtil.executeSqlQuery(connection, query);
    }
    //crud operations
    //Получение списка валют getAll+
    //get+
    //Получение списка всех обменных курсов
    //Получение конкретного обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса
    //
    //Post
    // Добавление новой валюты в базу+
    //Добавление нового обменного курса в базу. Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Поля формы - baseCurrencyCode, targetCurrencyCode, rate
    //
    //PATCH
    //Обновление существующего в базе обменного курса
    //Расчёт перевода определённого количества средств из одной валюты в другую
}
