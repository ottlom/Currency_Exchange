package validation;

import exception.BaseException;
import exception.DbNotAvailableException;
import exception.ExistStorageException;
import exception.NotExistStorageException;

import java.sql.SQLException;

public class SqlExceptionValidation {
    public static BaseException exchangeRateCheckDbException(SQLException exception) {
        BaseException returnException;
        if (exception.getMessage().contains("SQLITE_CONSTRAINT_NOTNULL")) {
            returnException = new NotExistStorageException("Одна (или обе) валюта из валютной пары не существует в БД");
        } else if (exception.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
            returnException = new ExistStorageException("Валютная пара с таким кодом уже существует");
        } else {
            returnException = new DbNotAvailableException("база данных недоступна");
        }
        return returnException;
    }

    public static BaseException currencyCheckDbException(SQLException exception) {
        BaseException returnException;
        if (exception.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
            returnException = new ExistStorageException("валюта с таким кодом уже существует");
        } else {
            returnException = new DbNotAvailableException("база данных недоступна");
        }
        return returnException;
    }
}