package dao;

import model.Currency;

import java.util.List;

public interface CurrencyDAO {
    void save(Currency currency);

    Currency get(String currency);

    List<Currency> getAll();
}
