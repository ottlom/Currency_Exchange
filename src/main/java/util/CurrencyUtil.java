package util;

import dto.CurrencyDto;
import model.Currency;

public class CurrencyUtil {
    public static CurrencyDto toCurrencyDTO(Currency currency) {
        return new CurrencyDto(currency.getId(), currency.getName(), currency.getCode(), currency.getSign());
    }
}

