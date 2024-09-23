package web;

import com.google.gson.Gson;
import exception.BaseException;
import util.ResponseHelperUtil;
import web.controller.ExchangeRateController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateController exchangeRateController = new ExchangeRateController();
    private final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(gson.toJson(exchangeRateController.getAll()));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseExchangeRate(response, exception);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            exchangeRateController.save(request.getParameter("baseCurrencyCode"), request.getParameter("targetCurrencyCode"), request.getParameter("rate"));
            response.getWriter().println(gson.toJson(
                    exchangeRateController.get(request.getParameter("baseCurrencyCode"), request.getParameter("targetCurrencyCode"))));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseExchangeRate(response, exception);
        }
    }
}