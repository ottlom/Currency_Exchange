package service;

import dao.CurrencyJdbcDAO;
import dao.ExchangeRateJdbcDAO;
import dto.ConversionCurrencyDto;
import model.ExchangeRate;
import util.CurrencyUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService {
    private final ExchangeRateJdbcDAO exchangeRateJdbcDAO = new ExchangeRateJdbcDAO();
    private final CurrencyJdbcDAO currencyJdbcDAO = new CurrencyJdbcDAO();

    public ConversionCurrencyDto changeCurrency(String baseCurrencyCode, String targetCurrencyCode, float amount) {
        float convertedAmount;
        ExchangeRate exchangeRate = getForExchange(baseCurrencyCode, targetCurrencyCode, amount);

        if (exchangeRate == null) {
            exchangeRate = new ExchangeRate(currencyJdbcDAO.get(baseCurrencyCode),
                    currencyJdbcDAO.get(targetCurrencyCode),
                    exchangeRateJdbcDAO.calculateCrossRate(baseCurrencyCode, targetCurrencyCode));
        }
        convertedAmount = doStraightConvertCurrency(exchangeRate.getRate(), amount);

        return new ConversionCurrencyDto(CurrencyUtil.toCurrencyDTO(currencyJdbcDAO.get(baseCurrencyCode)),
                CurrencyUtil.toCurrencyDTO(currencyJdbcDAO.get(targetCurrencyCode)), exchangeRate.getRate(),
                amount, convertedAmount);
    }

    private ExchangeRate getForExchange(String baseCurrenciesCode, String targetCurrenciesCode, float amount) {
        ExchangeRate exchangeRate = exchangeRateJdbcDAO.getExchangeRateWithoutNotExistException(baseCurrenciesCode, targetCurrenciesCode);
        if (exchangeRate == null) {
            exchangeRate = exchangeRateJdbcDAO.getExchangeRateWithoutNotExistException(targetCurrenciesCode, baseCurrenciesCode);
            if (exchangeRate != null) {
                exchangeRate.setRate(setReverseRate(exchangeRate.getRate(), amount));
            }
        }
        return exchangeRate;
    }

    private float doStraightConvertCurrency(float rate, float amount) {
        BigDecimal bigDecimal = new BigDecimal(rate * amount).setScale(2, RoundingMode.UP);
        return Float.parseFloat(bigDecimal.toString());
    }

    private float setReverseRate(float rate, float amount) {
        return amount / rate;
    }
}