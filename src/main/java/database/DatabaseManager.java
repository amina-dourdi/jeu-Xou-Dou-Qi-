package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String jdbc_url = "jdbc:h2:./xoudouqiDB;DB_CLOSE_DELAY=-1";
    private static final String user = "sa";
    private static final String password = "";
    private static final String driver = "org.h2.Driver";  // Nom complet du pilote JDBC pour la base de données H2

    private static Connection connection;
    
 // Constructeur privé pour empêcher l'instanciation
    private DatabaseManager() {
        try {
            Class.forName(driver); // Chargement explicite du driver H2
            connection = DriverManager.getConnection(jdbc_url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    // Méthode statique pour accéder à la connexion unique
    public static Connection getConnection() {
        if (connection == null) {
            new DatabaseManager(); // initialise la connexion
        }
        return connection;
    }
}
