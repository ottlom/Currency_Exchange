package web;

import com.google.gson.Gson;
import dto.ConversionCurrencyDto;
import exception.BaseException;
import util.ResponseHelperUtil;
import web.controller.ExchangeRateController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateController exchangeRateController = new ExchangeRateController();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            ConversionCurrencyDto conversionCurrencyDto = exchangeRateController.doExchange(request.getParameter("from"), request.getParameter("to"), request.getParameter("amount"));
            response.getWriter().println(gson.toJson(conversionCurrencyDto));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseExchangeRate(response, exception);
        }
    }
}