package modele;

public class Rat extends Animal{
	public Rat(Joueur proprietaire, Position position) {
        super("Rat", 1, position, proprietaire);
    }

    @Override
    public boolean peutSeDeplacer(Position vers, Plateau plateau) {
    	if (!this.position.estAdjacente(vers)) {
            return false;
        }

        Case destination = plateau.getCase(vers);
        if (destination == null) return false;

        Animal cible = destination.getAnimal();

        if (cible != null && cible.getProprietaire() == this.getProprietaire()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean peutCapturer(Animal autre) {
        if (autre == null) return false;
        if (autre.getProprietaire() == this.getProprietaire()) return false;

        if (autre.getNom().equalsIgnoreCase("Éléphant")) {
            return true;
        }

        return this.getForce() >= autre.getForce();
    }
}
