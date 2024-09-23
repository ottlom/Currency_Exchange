package dto;

public class ConversionCurrencyDto {
    private CurrencyDto baseCurrencyDto;
    private CurrencyDto targetCurrencyDto;
    private float rate;
    private float amount;
    private float convertedAmount;

    public ConversionCurrencyDto(CurrencyDto baseCurrencyDto, CurrencyDto targetCurrencyDto, float rate, float amount, float convertedAmount) {
        this.baseCurrencyDto = baseCurrencyDto;
        this.targetCurrencyDto = targetCurrencyDto;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
