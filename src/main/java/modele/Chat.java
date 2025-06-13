package modele;

public class Chat extends Animal{
	public Chat(Joueur proprietaire, Position position) {
        super("Chat", 2, position, proprietaire);
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
