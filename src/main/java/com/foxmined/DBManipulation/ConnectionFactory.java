package com.foxmined.DBManipulation;

import com.foxmined.Utils.ExceptionConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static ConnectionFactory instance;
    private final String URL = "jdbc:mysql://localhost:3306/school-console-app";
    private final String USER = "root";
    private final String PASSWORD = "4563";

    public Connection getConnection() {
        Connection dataBaseConnection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataBaseConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            return dataBaseConnection;
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_CONNECTION);
        }
        return null;
    }

    public static ConnectionFactory getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionFactory();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

}
