package Regle;

import modele.*;

public class GestionVictoire {
	public static boolean estVictoire(Joueur j, Plateau p) {
        for (int i = 0; i < Plateau.NB_LIGNES; i++) {
            for (int j2 = 0; j2 < Plateau.NB_COLONNES; j2++) {
                Case c = p.getCase(new Position(i, j2));
                if (c.getType() == TypeCase.SANCTUAIRE && c.getAnimal() != null && c.getAnimal().getProprietaire() == j) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean estPerdu(Joueur j, Plateau p) {
        // Si le joueur n'a plus d'animaux
        for (int i = 0; i < Plateau.NB_LIGNES; i++) {
            for (int j2 = 0; j2 < Plateau.NB_COLONNES; j2++) {
                Case c = p.getCase(new Position(i, j2));
                if (c.getAnimal() != null && c.getAnimal().getProprietaire() == j) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static int calculerScore(Joueur j, Plateau p) {
        int score = 0;
        for (int i = 0; i < Plateau.NB_LIGNES; i++) {
            for (int j2 = 0; j2 < Plateau.NB_COLONNES; j2++) {
                Case c = p.getCase(new Position(i, j2));
                Animal a = c.getAnimal();
                if (a != null && a.getProprietaire() == j) {
                    score += a.getForce(); // ou 1 point par animal, ou une pondération
                }
            }
        }
        return score;
    }
}
