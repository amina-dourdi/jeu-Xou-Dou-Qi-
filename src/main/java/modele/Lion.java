package modele;

public class Lion extends Animal{
	public Lion(Joueur proprietaire, Position position) {
        super("Lion", 7, position, proprietaire);
    }

    @Override
    public boolean peutSeDeplacer(Position vers, Plateau plateau) {
        // Deplacement normal
        if (this.position.estAdjacente(vers)) {
            Case destination = plateau.getCase(vers);
            if (destination == null) return false;
            if (destination.estEau()) return false;
            Animal cible = destination.getAnimal();
            if (cible == null) return true;
            return this.peutCapturer(cible);
        }

        // Saut la rivière
        if (this.position.estSurLigneDroite(vers)) {
            if (plateau.peutSauterRiviere(this.position, vers)) {
                Case destination = plateau.getCase(vers);
                if (destination == null) return false;
                Animal cible = destination.getAnimal();
                if (cible == null) return true;
                return this.peutCapturer(cible);
            }
        }

        return false;
    }
}
