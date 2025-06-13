package Regle;

import modele.*;

public class MoteurJeu {
	private Plateau plateau;
    private Joueur joueurCourant;
    private Joueur joueurAdverse;
    private boolean partieTerminee;
    private Joueur gagnant;

    public MoteurJeu(Joueur j1, Joueur j2) {
        plateau = new Plateau();
        plateau.placerAnimaux(j1, j2);
        this.joueurCourant = j1;
        this.joueurAdverse = j2;
        this.partieTerminee = false;
    }

    public boolean deplacerAnimal(Position from, Position to) {
        Animal animal = plateau.getCase(from).getAnimal();
        if (animal == null || animal.getProprietaire() != joueurCourant) return false;

        if (!GestionDeplacement.deplacementValide(animal, to, plateau)) return false;

        Case caseDestination = plateau.getCase(to);
        Animal cible = caseDestination.getAnimal();
        if (cible != null) {
            if (!GestionDeplacement.peutCapturer(animal, cible)) return false;
        }

        // Déplacement effectif
        plateau.getCase(from).setAnimal(null);
        caseDestination.setAnimal(animal);
        animal.setPosition(to);

        if (verifierVictoire()) {
            partieTerminee = true;
            gagnant = joueurCourant;
        } else {
            changerJoueur();
        }

        return true;
    }

    public boolean verifierVictoire() {
        return GestionVictoire.estVictoire(joueurCourant, plateau) ||
               GestionVictoire.estPerdu(joueurAdverse, plateau);
    }

    public void changerJoueur() {
        Joueur tmp = joueurCourant;
        joueurCourant = joueurAdverse;
        joueurAdverse = tmp;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    public Joueur getGagnant() {
        return gagnant;
    }
}
