package dao;

import model.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO {
    void save(String baseCurrenciesCode, String targetCurrenciesCode, double rate);

    void update(String baseCurrenciesCode, String targetCurrenciesCode, double rate);

    ExchangeRate get(String baseCurrenciesCode, String targetCurrenciesCode);

    List<ExchangeRate> getAll();
}
