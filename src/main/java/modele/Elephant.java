package modele;

public class Elephant extends Animal{
	public Elephant(Joueur proprietaire, Position position) {
        super("Éléphant", 8, position, proprietaire);
    }

    @Override
    public boolean peutSeDeplacer(Position vers, Plateau plateau) {
        if (!this.position.estAdjacente(vers)) {
            return false;
        }

        Case destination = plateau.getCase(vers);
        if (destination == null) {
            return false;
        }

        if (destination.estEau()) {
            return false;
        }

        Animal cible = destination.getAnimal();

        if (cible == null) {
            return true;
        }

        return this.peutCapturer(cible);
    }
}
