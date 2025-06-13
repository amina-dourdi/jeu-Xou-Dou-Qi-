package database;

import modele.Joueur;
import modele.Historique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueDAO {
    public void enregistrerPartie(Joueur j1, Joueur j2, Joueur gagnant, java.time.LocalDateTime datePartie) {
        String sql = "INSERT INTO historique(joueur1_id, joueur2_id, gagnant_id, date_partie) VALUES(?, ?, ?, ?)";
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, j1.getId());
            stmt.setInt(2, j2.getId());
            if (gagnant != null) stmt.setInt(3, gagnant.getId());
            else stmt.setNull(3, java.sql.Types.INTEGER);
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(datePartie));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur enregistrement partie : " + e.getMessage());
        }
    }

    public List<Historique> listerHistorique() {
        List<Historique> liste = new ArrayList<>();
        String sql = "SELECT joueur1_id, joueur2_id, gagnant_id, date_partie FROM historique";
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int j1 = rs.getInt("joueur1_id");
                int j2 = rs.getInt("joueur2_id");
                int g = rs.getInt("gagnant_id");
                Integer gagnant = rs.wasNull() ? null : g;
                java.time.LocalDateTime date = rs.getTimestamp("date_partie").toLocalDateTime();
                liste.add(new Historique(j1, j2, gagnant, date));
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération historique : " + e.getMessage());
        }
        return liste;
    }
}
