package validation;

import exception.EmptyDataFormException;

public class CurrencyValidation {
    public static void validateIncomingData(String name, String code, String sign) {
        checkEmptyForm(name, code, sign);
        incorrectDataValidation(name, code, sign);
        onlyEnglishAlphabetValidation(name, code);
        lengthSignValidation(sign);
        currencyCodeValidation(code);
    }

    private static void checkEmptyForm(String name, String code, String sign) {
        if (name == null || code == null || sign == null) {
            throw new EmptyDataFormException("Отсутствует нужное поле формы");
        }
    }

    private static void incorrectDataValidation(String... params) {
        for (String param : params) {
            if (param == null || param.trim().isEmpty() || param.matches(".*\\d.*")) {
                throw new EmptyDataFormException("цифры либо пустые значения недопустимы");
            }
        }
    }

    private static void lengthSignValidation(String sign) {
        if (sign.length() > 6) {
            throw new EmptyDataFormException("длина символа валюты ограничена 6 символами");
        }
    }

    public static void currencyCodeValidation(String code) {
        if (code.length() != 3 || !code.matches("[a-zA-Z]+")) {
            throw new EmptyDataFormException("код валюты должен иметь вид XXX");
        }
    }

    private static void onlyEnglishAlphabetValidation(String... params) {
        for (String param : params) {
            if (!param.matches("[a-zA-Z ]+")) {
                throw new EmptyDataFormException("заполнение полей производятся на английсском");
            }
        }
    }
}
