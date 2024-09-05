package dto;

public class ExchangeRateDto {
    private int id;
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    private float rate;

    public ExchangeRateDto(CurrencyDto baseCurrency, CurrencyDto targetCurrency, float rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRateDto(int id, CurrencyDto baseCurrency, CurrencyDto targetCurrency, float rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyDto getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyDto baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyDto getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyDto targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate " +
                "id= " + id +
                ", baseCurrency= " + baseCurrency +
                ", targetCurrency= " + targetCurrency +
                ", rate= " + rate;
    }
}
