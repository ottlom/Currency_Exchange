package web.controller;

import dao.ExchangeRateJdbcDAO;
import dto.ConversionCurrencyDto;
import dto.ExchangeRateDto;
import model.ExchangeRate;
import service.ExchangeService;
import util.ExchangeRateUtil;
import validation.ExchangeRateValidation;
import validation.ExchangeValidation;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateController {
    private final ExchangeRateJdbcDAO exchangeRateJdbcDAO = new ExchangeRateJdbcDAO();

    public List<ExchangeRateDto> getAll() {
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRateJdbcDAO.getAll()) {
            exchangeRateDtoList.add(ExchangeRateUtil.toExchangeRateDTO(exchangeRate));
        }
        return exchangeRateDtoList;
    }

    public ExchangeRateDto get(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRateValidation.currencyCodeValidation(baseCurrencyCode, targetCurrencyCode);
        return ExchangeRateUtil.toExchangeRateDTO(exchangeRateJdbcDAO.get(baseCurrencyCode, targetCurrencyCode));
    }

    public void save(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        ExchangeRateValidation.validateIncomingData(baseCurrencyCode, targetCurrencyCode, rate);
        exchangeRateJdbcDAO.save(baseCurrencyCode.toUpperCase(), targetCurrencyCode.toUpperCase(), Float.parseFloat(rate));
    }

    public void update(ExchangeRateDto exchangeRateDto, String rate) {
        float checkedRate = ExchangeRateValidation.rateValidation(rate);
        exchangeRateJdbcDAO.update(exchangeRateDto.getBaseCurrency().getCode().toUpperCase(), exchangeRateDto.getTargetCurrency().getCode().toUpperCase(), checkedRate);
    }

    public ConversionCurrencyDto doExchange(String baseCurrencyCode, String targetCurrencyCode, String amount) {
        ExchangeService exchangeService = new ExchangeService();
        ExchangeValidation.exchangeValidation(baseCurrencyCode, targetCurrencyCode, amount);
        return exchangeService.changeCurrency(baseCurrencyCode, targetCurrencyCode, Float.parseFloat(amount));
    }
}