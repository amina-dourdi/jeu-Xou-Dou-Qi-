package Regle;

import modele.*;

public class GestionTour {
	private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;

    public GestionTour(Joueur j1, Joueur j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurActuel = j1;
    }

    public Joueur joueurSuivant() {
        joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
        return joueurActuel;
    }

    public void debuterTour() {
        System.out.println("Tour de " + joueurActuel.getUsername());
    }

    public void terminerTour() {
        joueurSuivant();
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }
}
