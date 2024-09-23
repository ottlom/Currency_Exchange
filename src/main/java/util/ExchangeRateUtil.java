package util;

import dto.ExchangeRateDto;
import exception.BaseException;
import model.ExchangeRate;

public class ExchangeRateUtil {
    public static ExchangeRateDto toExchangeRateDTO(ExchangeRate exchangeRate) throws BaseException {
        return new ExchangeRateDto(exchangeRate.getId(), CurrencyUtil.toCurrencyDTO(exchangeRate.getBaseCurrency()), CurrencyUtil.toCurrencyDTO(exchangeRate.getTargetCurrency()), exchangeRate.getRate());
    }
}
