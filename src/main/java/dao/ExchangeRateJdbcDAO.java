package dao;

import dao.jdbc.Jdbc;
import model.Currency;
import util.SqlUtil;
import model.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateJdbcDAO implements ExchangeRateDAO {
    Connection connection = Jdbc.getJdbc().getConnection();
    SqlUtil sqlUtil = new SqlUtil();

    @Override
    public void save(String baseCurrenciesCode, String targetCurrenciesCode, float rate) {
        try (PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                "INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate) " +
                        "VALUES ((SELECT id FROM currencies WHERE code=?), (SELECT id FROM currencies WHERE code=?), ?)")) {
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            preparedStatement.setFloat(3, rate);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String baseCurrenciesCode, String targetCurrenciesCode, float rate) {
        try (PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                "UPDATE exchange_rates " +
                        "SET rate = ? " +
                        "WHERE base_currency_id = (SELECT id FROM currencies WHERE code=?) " +
                        "AND target_currency_id = (SELECT id FROM currencies WHERE code=?)")) {
            preparedStatement.setFloat(1, rate);
            preparedStatement.setString(2, baseCurrenciesCode);
            preparedStatement.setString(3, targetCurrenciesCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExchangeRate get(String baseCurrenciesCode, String targetCurrenciesCode) {
        ExchangeRate exchangeRate = null;
        try (PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                "SELECT e.id AS exchange_rate_id, e.rate, " +
                        "c_base.id AS base_currency_id, c_base.full_name AS base_currency_name, c_base.code AS base_currency_code, c_base.sign AS base_currency_sign, " +
                        "c_target.id AS target_currency_id, c_target.full_name AS target_currency_name, c_target.code AS target_currency_code, c_target.sign AS target_currency_sign " +
                        "FROM exchange_rates e " +
                        "LEFT JOIN currencies c_base ON e.base_currency_id = c_base.id " +
                        "LEFT JOIN currencies c_target ON e.target_currency_id = c_target.id " +
                        "WHERE c_base.code=? AND c_target.code=? ")) {
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exchangeRate = new ExchangeRate(
                        resultSet.getInt("exchange_rate_id"),
                        new Currency(resultSet.getInt("base_currency_id"),
                                resultSet.getString("base_currency_name"),
                                resultSet.getString("base_currency_code"),
                                resultSet.getString("base_currency_sign")),
                        new Currency(resultSet.getInt("target_currency_id"),
                                resultSet.getString("target_currency_name"),
                                resultSet.getString("target_currency_code"),
                                resultSet.getString("target_currency_sign")),
                        resultSet.getFloat("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                "SELECT e.id AS exchange_rate_id, e.rate, " +
                        "c_base.id AS base_currency_id, c_base.full_name AS base_currency_name, c_base.code AS base_currency_code, c_base.sign AS base_currency_sign, " +
                        "c_target.id AS target_currency_id, c_target.full_name AS target_currency_name, c_target.code AS target_currency_code, c_target.sign AS target_currency_sign " +
                        "FROM exchange_rates e " +
                        "LEFT JOIN currencies c_base ON e.base_currency_id = c_base.id " +
                        "LEFT JOIN currencies c_target ON e.target_currency_id = c_target.id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new ExchangeRate(
                        resultSet.getInt("exchange_rate_id"),
                        new Currency(resultSet.getInt("base_currency_id"),
                                resultSet.getString("base_currency_name"),
                                resultSet.getString("base_currency_code"),
                                resultSet.getString("base_currency_sign")),
                        new Currency(resultSet.getInt("target_currency_id"),
                                resultSet.getString("target_currency_name"),
                                resultSet.getString("target_currency_code"),
                                resultSet.getString("target_currency_sign")),
                        resultSet.getFloat("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
