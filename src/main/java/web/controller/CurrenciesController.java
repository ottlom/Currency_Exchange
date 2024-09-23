package web.controller;

import dao.CurrencyJdbcDAO;
import dto.CurrencyDto;
import model.Currency;
import util.CurrencyUtil;
import validation.CurrencyValidation;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesController {
    private final CurrencyJdbcDAO currencyJdbcDAO = new CurrencyJdbcDAO();

    public CurrencyDto get(String code) {
        CurrencyValidation.currencyCodeValidation(code);
        return CurrencyUtil.toCurrencyDTO(currencyJdbcDAO.get(code));
    }

    public void save(CurrencyDto currencyDto) {
        CurrencyValidation.validateIncomingData(currencyDto.getName(), currencyDto.getCode(), currencyDto.getSign());
        currencyJdbcDAO.save(new Currency(currencyDto.getName(), currencyDto.getCode(), currencyDto.getSign()));
    }

    public List<CurrencyDto> getAll() {
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        for (Currency currency : currencyJdbcDAO.getAll()) {
            currencyDtoList.add(CurrencyUtil.toCurrencyDTO(currency));
        }
        return currencyDtoList;
    }
}
