package validation;

import exception.EmptyDataFormException;

public class ExchangeValidation {
    public static void exchangeValidation(String from, String to, String amount) {
        validateIncomingData(from, to, amount);
        amountValidation(amount);
        sameCurrencyValidation(from, to);
    }

    private static void validateIncomingData(String from, String to, String amount) {
        if (from == null || to == null || amount == null ||
                from.trim().isEmpty() || to.trim().isEmpty() || amount.trim().isEmpty()) {
            throw new EmptyDataFormException("Отсутствует нужное поле формы");
        }
    }

    private static void amountValidation(String amount) {
        if (!amount.matches("-?\\d+(\\.\\d+)?")) {
            throw new EmptyDataFormException("допустимо вводить только числа");
        }
        if (Float.parseFloat(amount) <= 0 || !amount.matches("^(0|[1-9]\\d*)(\\.\\d+)?$")) {
            throw new EmptyDataFormException("необходимо ввести число больше 0");
        }
    }

    private static void sameCurrencyValidation(String from, String to) {
        if (from.equals(to)) {
            throw new EmptyDataFormException("выбраны одинаковые валюты");
        }
    }
}