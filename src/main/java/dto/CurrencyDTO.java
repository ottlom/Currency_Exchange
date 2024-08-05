package dto;

public class CurrencyDTO {
    private String code;
    private String name;
    private char sign;

    public CurrencyDTO(String code, String name, char sign) {
        this.code = code;
        this.name = name;
        this.sign = sign;
    }
}
