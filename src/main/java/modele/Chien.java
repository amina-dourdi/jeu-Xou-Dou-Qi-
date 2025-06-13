package modele;

public class Chien extends Animal{
	public Chien(Joueur proprietaire, Position position) {
        super("Chien", 4, position, proprietaire);
    }

    @Override
    public boolean peutSeDeplacer(Position vers, Plateau plateau) {
        if (!this.position.estAdjacente(vers)) {
            return false;
        }

        Case destination = plateau.getCase(vers);
        if (destination == null) return false;

        if (destination.estEau()) return false;

        Animal cible = destination.getAnimal();

        if (cible == null) return true;

        return this.peutCapturer(cible);
    }
}
