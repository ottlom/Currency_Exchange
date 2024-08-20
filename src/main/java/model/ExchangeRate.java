package model;

public class ExchangeRate {
    private int baseCurrencyId;
    private int targetCurrencyId;
    private float rate;

    public ExchangeRate(int baseCurrencyId, int targetCurrencyId, float rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }

    public int getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(int baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public int getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(int targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "baseCurrency=" + baseCurrencyId +
                ", targetCurrency=" + targetCurrencyId +
                ", rate=" + rate +
                '}';
    }
}
