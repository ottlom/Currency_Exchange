package util;

import dto.CurrencyDTO;
import model.Currency;

public class CurrencyUtil {
    public CurrencyDTO toCurrencyDTO(Currency currency) {
        return new CurrencyDTO(currency.getCode(), currency.getName(), currency.getSign());
    }
}

