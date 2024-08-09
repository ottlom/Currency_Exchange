import controller.CurrencyExchangeController;
import model.Currency;
import servlet.ExchangeServlet;

public class MainApp {
    public static void main(String[] args) {
        CurrencyExchangeController controller = new CurrencyExchangeController();
//        System.out.println(controller.getAllCurrency());
//        System.out.println("------");
//        System.out.println(controller.get("USD"));
//        System.out.println("------");
//        //System.out.println(controller.save(new Currency("SEK", "Swedish Krona", "kr")));
//        System.out.println("------");
//        System.out.println(controller.update(new Currency("SEK", "Swedish Krona", "kr")));
//        controller.delete("SEK");
        System.out.println(controller.getAllRate());
        ExchangeServlet servlet = new ExchangeServlet();
    }
}
