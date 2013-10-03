package br.ifrn.tads.poo.banco.app;
import java.sql.*;

public class ConnectionFactory {
	public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemabancario-fg","root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
