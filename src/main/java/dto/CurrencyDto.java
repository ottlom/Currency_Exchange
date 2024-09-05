package dto;

public class CurrencyDto {
    private int id;
    private final String name;
    private final String code;
    private final String sign;

    public CurrencyDto(String name, String code, String sign) {
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public CurrencyDto(int id, String name, String code, String sign) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Currency: " + id + ", " + name + ", " + code + ", " + sign;
    }
}
