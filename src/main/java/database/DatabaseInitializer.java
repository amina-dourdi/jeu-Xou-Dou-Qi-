package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
	public static void initialiserTables() {
        try (Connection con = DatabaseManager.getConnection();
             Statement stmt = con.createStatement()) {

            // Table joueur
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS joueur (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nom VARCHAR(100) UNIQUE NOT NULL,
                    mot_de_passe VARCHAR(100) NOT NULL
                );
            """);

            // Table historique
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS historique (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    joueur1_id INT NOT NULL,
                    joueur2_id INT NOT NULL,
                    gagnant_id INT,  -- peut être NULL si égalité
                    date_partie TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (joueur1_id) REFERENCES joueur(id),
                    FOREIGN KEY (joueur2_id) REFERENCES joueur(id),
                    FOREIGN KEY (gagnant_id) REFERENCES joueur(id)
                );
            """);

            System.out.println("Tables initialisees avec succes.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l initialisation des tables : " + e.getMessage());
        }
    }
}
