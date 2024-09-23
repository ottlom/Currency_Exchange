package web;

import com.google.gson.Gson;
import dto.CurrencyDto;
import exception.BaseException;
import exception.EmptyDataFormException;
import util.ResponseHelperUtil;
import web.controller.CurrenciesController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CurrencyServlet extends HttpServlet {
    private final CurrenciesController controller = new CurrenciesController();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CurrencyDto currency = controller.get(getCurrencyCodeFromUrl(request, response));
            response.setContentType("application/json");
            response.getWriter().println(gson.toJson(currency));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseCurrency(response, exception);
        }
    }

    private String getCurrencyCodeFromUrl(HttpServletRequest request, HttpServletResponse response) throws BaseException {
        String path = request.getPathInfo();
        response.setContentType("application/json; charset=UTF-8");
        if (path.substring(1).length() != 3) {
            throw new EmptyDataFormException("Код валюты отсутствуют в адресе");
        } else {
            return path.substring(1).toUpperCase();
        }
    }
}