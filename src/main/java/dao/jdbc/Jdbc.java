package dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Jdbc {
    private Connection connection;

    public Jdbc() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Java\\projects\\Currency_Exchange\\src\\main\\resources\\currency_exchange_db");
        } catch (SQLException e) {
            System.out.println("не удалось подключиться к базе");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}