package bodega_myc_.ConexionMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionClass {
    private final String URL = "jdbc:mysql://localhost:3306/bodega_myc?"
        + "useSSL=false&"
        + "autoReconnect=true&"
        + "failOverReadOnly=false&"
        + "maxReconnects=10&"
        + "connectTimeout=5000&"
        + "socketTimeout=30000&"
        + "serverTimezone=UTC";
    
    private final String USER = "root";
    private final String PASSWORD = "";

    public Connection getConexion() {
        try {
            // Cargar el driver (no siempre necesario en versiones recientes)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Crear nueva conexión cada vez
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage(), e);
        }
    }
}