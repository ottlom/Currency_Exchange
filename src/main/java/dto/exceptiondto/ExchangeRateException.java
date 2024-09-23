package dto.exceptiondto;

public class ExchangeRateException {
    private final String message;

    public ExchangeRateException(String message) {
        this.message = message;
    }
}