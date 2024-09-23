package web;

import com.google.gson.Gson;
import dto.CurrencyDto;
import exception.BaseException;
import util.ResponseHelperUtil;
import web.controller.CurrenciesController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CurrenciesServlet extends HttpServlet {
    private final CurrenciesController controller = new CurrenciesController();
    private final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(gson.toJson(controller.getAll()));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseCurrency(response, exception);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            CurrencyDto currencyDTO = new CurrencyDto(request.getParameter("name"), request.getParameter("code").toUpperCase(), request.getParameter("sign"));
            controller.save(currencyDTO);
            response.getWriter().println(gson.toJson(controller.get(currencyDTO.getCode())));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (BaseException exception) {
            ResponseHelperUtil.prepareResponseCurrency(response, exception);
        }
    }
}