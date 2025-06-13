package database;
import modele.Joueur;
import java.sql.*;
public class JoueurDAO {

    public Joueur creerJoueur(String nom, String motDePasse) {
        String sql = "INSERT INTO joueur(nom, mot_de_passe) VALUES(?, ?)";
        try {
            Connection con = DatabaseManager.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nom);
                stmt.setString(2, motDePasse);
                int rows = stmt.executeUpdate();
                if (rows == 0) throw new SQLException("Créer joueur a échoué, aucune ligne affectée.");

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        return new Joueur(id, nom, motDePasse);
                    }
                    throw new SQLException("Créer joueur a échoué, aucun ID généré.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du joueur : " + e.getMessage());
            return null;
        }
    }

    public Joueur connecterJoueur(String nom, String motDePasse) {
        String sql = "SELECT id, nom, mot_de_passe FROM joueur WHERE nom=? AND mot_de_passe=?";
        try {
            Connection con = DatabaseManager.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, nom);
                stmt.setString(2, motDePasse);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Joueur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("mot_de_passe")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur connexion joueur : " + e.getMessage());
        }
        return null;
    }
}
