package validation;

import exception.EmptyDataFormException;

public class ExchangeRateValidation {
    public static void validateIncomingData(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        emptyValidation(baseCurrencyCode, targetCurrencyCode, rate);
        sameCurrencyValidation(baseCurrencyCode, targetCurrencyCode);
        rateValidation(rate);
    }

    public static float rateValidation(String rate) {
        formatValidation(rate);
        return parseRate(rate);
    }

    public static void currencyCodeValidation(String... codes) {
        for (String code : codes) {
            if (!code.matches("[a-zA-Z]+")) {
                throw new EmptyDataFormException("код валют пишется вместе должен иметь вид XXXYYY на английском языке");
            }
        }
    }

    private static void emptyValidation(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null ||
                baseCurrencyCode.trim().isEmpty() || targetCurrencyCode.trim().isEmpty() || rate.trim().isEmpty()) {
            throw new EmptyDataFormException("Отсутствует нужное поле формы");
        }
    }

    private static void formatValidation(String rate) {
        if (!rate.matches("-?\\d+(\\.\\d+)?")) {
            throw new EmptyDataFormException("допустимо вводить только числа");
        }
        if (Float.parseFloat(rate) <= 0 || !rate.matches("^(0|[1-9]\\d*)(\\.\\d+)?$")) {
            throw new EmptyDataFormException("необходимо ввести число больше 0");
        }
    }

    private static float parseRate(String rate) {
        return Float.parseFloat(rate);
    }

    private static void sameCurrencyValidation(String baseCurrencyCode, String targetCurrencyCode) {
        if (baseCurrencyCode.equals(targetCurrencyCode)) {
            throw new EmptyDataFormException("выбраны одинаковые валюты");
        }
    }
}
