package service;

import dao.ExchangeRateJdbcDAO;
import dao.jdbc.Jdbc;
import util.SqlUtil;
import model.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateService {
    ExchangeRateJdbcDAO exchangeRateJdbcDAO;

    public float changeCurrency(String baseCurrenciesCode, String targetCurrenciesCode, int amount) {
        ExchangeRate rate = exchangeRateJdbcDAO.get(baseCurrenciesCode, targetCurrenciesCode);
        float convertedAmount;

        if (rate != null) {
            convertedAmount = rate.getRate() * amount;
        } else {
            rate = exchangeRateJdbcDAO.get(targetCurrenciesCode, baseCurrenciesCode);
            if (rate != null) {
                convertedAmount = amount / rate.getRate();
            } else {
                convertedAmount = changeWithoutStraightRate(baseCurrenciesCode, targetCurrenciesCode, amount);
            }
        }
        return convertedAmount;
    }

    private float changeWithoutStraightRate(String baseCurrenciesCode, String targetCurrenciesCode, int amount) {
        SqlUtil sqlUtil = new SqlUtil();
        List<Float> result = new ArrayList<>(2);
        try {
            PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(Jdbc.getJdbc().getConnection(),
                    "SELECT rate FROM exchange_rates " +
                            "WHERE base_currency_id = (SELECT id FROM currencies WHERE code='USD') AND target_currency_id = (SELECT id FROM currencies WHERE code=?) " +
                            "OR base_currency_id = (SELECT id FROM currencies WHERE code='USD') AND target_currency_id = (SELECT id FROM currencies WHERE code=?)");
            preparedStatement.setString(1, baseCurrenciesCode);
            preparedStatement.setString(2, targetCurrenciesCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getFloat("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.get(1) / result.get(0) * amount;
    }
}