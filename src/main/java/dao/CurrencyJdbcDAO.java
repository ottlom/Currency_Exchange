package dao;

import dao.jdbc.Jdbc;
import util.SqlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Currency;

import java.util.List;

public class CurrencyJdbcDAO implements CurrencyDAO {
    Connection connection = Jdbc.getJdbc().getConnection();
    SqlUtil sqlUtil = new SqlUtil();

    @Override
    public void save(Currency currency) {
        try {
            PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                    "INSERT INTO currencies (full_name, code, sign) VALUES (?,?,?)");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Currency currency) {
        try {
            PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection,
                    "UPDATE currencies SET full_name=?, code=?, sign=? WHERE code=?");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.setString(4, currency.getCode());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Currency get(String currencyCode) {
        Currency currency = new Currency();
        try {
            PreparedStatement preparedStatement = sqlUtil.executeSqlQuery(connection, "SELECT * FROM currencies WHERE code=?");
            preparedStatement.setString(1, currencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();
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

    @Override
    public List<Currency> getAll() {
        List<Currency> currencyList = new ArrayList<>();
        try {
            ResultSet resultSet = sqlUtil.executeSqlQuery(connection, "SELECT * FROM currencies").executeQuery();
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
}
