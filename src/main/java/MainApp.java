import controller.CurrencyExchangeController;

public class MainApp {
    public static void main(String[] args) {
        CurrencyExchangeController controller = new CurrencyExchangeController();
//        System.out.println(controller.getAllCurrency());
//        System.out.println("------");
//        System.out.println(controller.get("USD"));
//        System.out.println("------");
//          controller.save(new Currency("CHF", "Swiss Franc", "CHF"));
//        System.out.println("------");
//        System.out.println(controller.update(new Currency("SEK", "Swedish Krona", "kr")));
//        controller.delete("SEK");
//        System.out.println(controller.getAllRate());
//        ExchangeServlet servlet = new ExchangeServlet();
//        controller.saveRate("CHF","USD", 1.15);
//        System.out.println(controller.getExchangeRate("CHF","USD"));
        controller.updateExchangeRate("CHF","USD", 1.15);
    }
}
