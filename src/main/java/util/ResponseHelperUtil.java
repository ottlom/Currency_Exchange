package util;

import com.google.gson.Gson;
import dto.exceptiondto.CurrencyException;
import dto.exceptiondto.ExchangeRateException;
import exception.BaseException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHelperUtil {
    public static void prepareResponseExchangeRate(HttpServletResponse response, BaseException exception) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(exception.getStatusResponse());
        response.getWriter().print(new Gson().toJson(new ExchangeRateException(exception.getMessage())));
    }

    public static void prepareResponseCurrency(HttpServletResponse response, BaseException exception) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(exception.getStatusResponse());
        response.getWriter().print(new Gson().toJson(new CurrencyException(exception.getMessage())));
    }
}
