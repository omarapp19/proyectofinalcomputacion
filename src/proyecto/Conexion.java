package proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conexion {

    
    private static final String URL = "jdbc:sqlite:dojo.db";


    private static Conexion instancia;
    

    private Connection connection;

  
    private Conexion() {
        try {
         
            Class.forName("org.sqlite.JDBC");
            
          
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Error: No se encontró el driver de SQLite (sqlite-jdbc.jar).\n" +
                "Asegúrate de añadir el .JAR a las 'Libraries' del proyecto.",
                "Error Crítico de Base de Datos", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }
    }

 
    public static Conexion getInstance() {
     
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

 
    public Connection conectar() {
        try {
 
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                System.out.println("Conexión a SQLite establecida con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con la base de datos: " + e.getMessage(),
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }


    public void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a SQLite cerrada.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cerrar la conexión: " + e.getMessage(),
                "Error de Desconexión", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
  
    public static Connection getConexion() {
        return Conexion.getInstance().conectar();
    }

}
