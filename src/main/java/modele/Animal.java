package modele;

public abstract class Animal {
    protected String nom;
    protected int force;
    protected Position position;
    protected Joueur proprietaire;

    public Animal(String nom, int force, Position position, Joueur proprietaire) {
        this.nom = nom;
        this.force = force;
        this.position = position;
        this.proprietaire = proprietaire;
    }

    public String getNom() { return nom; }
    public int getForce() { return force; }
    public Position getPosition() { return position; }
    public void setPosition(Position p) { this.position = p; }
    public Joueur getProprietaire() { return proprietaire; }

    public abstract boolean peutSeDeplacer(Position vers, Plateau plateau);

    public boolean peutCapturer(Animal autre) {
        if (autre == null) return false;
        if (autre.getProprietaire() == this.proprietaire) return false;
        if (this.nom.equals("Rat") && autre.getNom().equals("Éléphant")) return true;
        return this.force >= autre.force;
    }
    
    @Override
    public String toString() {
        return Case.abrevAnimal(nom) + proprietaire.getNumero();
    }
}

