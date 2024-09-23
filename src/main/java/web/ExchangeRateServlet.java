package web;

import com.google.gson.Gson;
import dto.ExchangeRateDto;
import exception.BaseException;
import exception.EmptyDataFormException;
import util.ResponseHelperUtil;
import web.controller.ExchangeRateController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateController exchangeRateController = new ExchangeRateController();
    private final Gson gson = new Gson();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getMethod().equals("PATCH")) {
            doPost(request, response);
        } else if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String exchangeRateCode = getCurrencyCodeFromUrl(request, response);
            ExchangeRateDto exchangeRateDto = exchangeRateController.get(exchangeRateCode.substring(0, 3), exchangeRateCode.substring(3, 6));
            response.getWriter().println(gson.toJson(exchangeRateDto));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseExchangeRate(response, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String exchangeRateCode = getCurrencyCodeFromUrl(request, response);
            ExchangeRateDto exchangeRateDto = exchangeRateController.get(exchangeRateCode.substring(0, 3).toUpperCase(), exchangeRateCode.substring(3, 6).toUpperCase());
            exchangeRateController.update(exchangeRateDto, getRateFromRequest(request));
            response.getWriter().println(gson.toJson(exchangeRateController.get(exchangeRateDto.getBaseCurrency().getCode(), exchangeRateDto.getTargetCurrency().getCode())));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseExchangeRate(response, exception);
        }
    }

    private String getCurrencyCodeFromUrl(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo();
        response.setContentType("application/json; charset=UTF-8");
        if (path.substring(1).length() < 6) {
            throw checkRequestMethod(request);
        } else {
            return path.substring(1);
        }
    }

    private String getRateFromRequest(HttpServletRequest request) {
        String rate;
        try {
            rate = request.getReader().readLine().substring(5);
        } catch (Exception exception) {
            throw new EmptyDataFormException("Отсутствует нужное поле формы");
        }
        return rate;
    }

    private EmptyDataFormException checkRequestMethod(HttpServletRequest request) {
        if (request.getMethod().equals("PATCH")) {
            return new EmptyDataFormException("Отсутствует нужное поле формы");
        } else {
            return new EmptyDataFormException("Коды валют пары отсутствуют в адресе");
        }
    }
}