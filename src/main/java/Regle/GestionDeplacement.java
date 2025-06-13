package Regle;

import modele.*;

public class GestionDeplacement {
	public static boolean deplacementValide(Animal a, Position to, Plateau p) {
        return a.peutSeDeplacer(to, p);
    }

    public static boolean peutSauterRiviere(Animal a, Position from, Position to, Plateau p) {
        return p.peutSauterRiviere(from, to);
    }

    public static boolean peutCapturer(Animal attaquant, Animal cible) {
        return attaquant.peutCapturer(cible);
    }
}
