package dao;

import dao.jdbc.Jdbc;
import exception.DbNotAvailableException;
import exception.NotExistStorageException;
import model.Currency;
import util.SqlUtil;
import validation.SqlExceptionValidation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyJdbcDAO implements CurrencyDAO {
    private final Jdbc jdbc = new Jdbc();

    @Override
    public void save(Currency currency) {
        try {
            PreparedStatement preparedStatement = SqlUtil.executeSqlQuery(jdbc.getConnection(),
                    "INSERT INTO currencies (full_name, code, sign) VALUES (?,?,?)");
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCode().toUpperCase());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw SqlExceptionValidation.currencyCheckDbException(exception);
        }
    }

    @Override
    public Currency get(String currencyCode) {
        Currency currency = new Currency();
        try (PreparedStatement preparedStatement = SqlUtil.executeSqlQuery(jdbc.getConnection(), "SELECT * FROM currencies WHERE code=?")) {
            preparedStatement.setString(1, currencyCode.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currency.setId(resultSet.getInt("id"));
                currency.setName(resultSet.getString("full_name"));
                currency.setCode(resultSet.getString("code").toUpperCase());
                currency.setSign(resultSet.getString("sign"));
            }
            if (currency.getCode() == null || currency.getId() == 0) {
                throw new NotExistStorageException("Валюта не найдена");
            }
        } catch (SQLException exception) {
            throw new DbNotAvailableException("база данных недоступна");
        }
        return currency;
    }

    @Override
    public List<Currency> getAll() {
        List<Currency> currencyList = new ArrayList<>();
        try (PreparedStatement preparedStatement = SqlUtil.executeSqlQuery(jdbc.getConnection(), "SELECT * FROM currencies")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currencyList.add(new Currency(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("code"),
                        resultSet.getString("sign")));
            }
        } catch (SQLException exception) {
            throw SqlExceptionValidation.currencyCheckDbException(exception);
        }
        return currencyList;
    }
}
