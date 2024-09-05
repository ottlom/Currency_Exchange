import dto.ExchangeRateDto;
import web.controller.CurrenciesController;
import web.controller.ExchangeRateController;

public class MainApp {
    public static void main(String[] args) {
        CurrenciesController currenciesController = new CurrenciesController();
        ExchangeRateController exchangeRateController = new ExchangeRateController();
//        System.out.println(currencyJdbcDAO.getAll());
//        System.out.println("------");
//        System.out.println(controller.get("USD"));
//        System.out.println("------");
//          controller.save(new Currency("CHF", "Swiss Franc", "CHF"));
//        System.out.println("------");
//        System.out.println(controller.update(new Currency("SEK", "Swedish Krona", "kr")));
//        controller.delete("SEK");
//        System.out.println(controller.getAllRate());
//        ExchangeRateServlet servlet = new ExchangeRateServlet();
//        controller.saveRate("USD", "CHF", 0.87);
//        System.out.println(controller.getExchangeRate("USD","RUB"));
//        System.out.println(controller.changeCurrency("RUB", "SEK", 1));
//        ExchangeRateDto exchangeRate = new ExchangeRateDto(currenciesController.get("USD"), currenciesController.get("TRY"), 33);
//        System.out.println(exchangeRate);
//        exchangeRateController.update(exchangeRate, 36);
//        exchangeRate = exchangeRateController.get("USD", "TRY");
//        System.out.println(exchangeRate);
    }
}
