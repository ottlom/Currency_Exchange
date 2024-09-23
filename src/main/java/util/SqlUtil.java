package util;

import exception.DbNotAvailableException;
import model.Currency;
import model.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtil {
    public static PreparedStatement executeSqlQuery(Connection connection, String sql) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DbNotAvailableException("база данных недоступна");
        }
        return preparedStatement;
    }

    public static ExchangeRate initializeExchangeRate(ResultSet resultSet) throws SQLException {
        ExchangeRate exchangeRate = null;
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
        return exchangeRate;
    }
}