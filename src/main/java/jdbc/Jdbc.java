package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
    private static Jdbc jdbc;
    private Connection connection;

    private Jdbc() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:currency_exchange_db");
        } catch (SQLException e) {
            System.out.println("не удалось подключиться к базе");
        }
    }

    public static Jdbc getJdbc() {
        if (jdbc == null) {
            return jdbc = new Jdbc();
        } else {
            return jdbc;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
