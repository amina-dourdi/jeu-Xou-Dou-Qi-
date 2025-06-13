package console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.*;
import modele.*;

public class AfficheJoueurs {
	public static void main(String[] args) {
		// Initialise les tables si besoin (à décommenter si jamais)
        // DatabaseInitializer.initialiserTables();
        List<Joueur> joueurs = listerTousLesJoueurs();

        if (joueurs.isEmpty()) {
            System.out.println("Aucun joueur trouvé dans la base de données.");
        } else {
            System.out.println("Liste des joueurs enregistrés :");
            for (Joueur j : joueurs) {
                System.out.println("ID: " + j.getNumero() + "\t, Nom: " + j.getUsername() + "\t, Mot de passe: " + j.getMotDePasse());
            }
        }
    }

    private static List<Joueur> listerTousLesJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur";

        try {
            Connection con = DatabaseManager.getConnection(); // Connexion partagée, NE PAS fermer
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String motDePasse = rs.getString("mot_de_passe");  // récupère le mot de passe
                    Joueur joueur = new Joueur(nom, motDePasse, id); // passe le mot de passe au constructeur
                    joueurs.add(joueur);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des joueurs : " + e.getMessage());
        }

        return joueurs;
    }
}
