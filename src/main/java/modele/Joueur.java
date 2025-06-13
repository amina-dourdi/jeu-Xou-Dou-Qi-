package modele;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private int id;                  // identifiant persistant en base
    private String username;
    private String motDePasse;
    private int numero;              // 1 ou 2 pour le tour de jeu

    private int nbPartiesGagnees;
    private int nbPartiesPerdues;
    private int egalites;

    private List<Animal> animaux;

    /**
     * Constructeur utilisé pour la persistance (avec l’ID).
     */
    public Joueur(int id, String username, String motDePasse) {
        this.id = id;
        this.username = username;
        this.motDePasse = motDePasse;
        this.nbPartiesGagnees = 0;
        this.nbPartiesPerdues = 0;
        this.egalites = 0;
        this.animaux = new ArrayList<>();
    }

    /**
     * Constructeur pour l’usage en partie (sans ID initial).
     */
    public Joueur(String username, String motDePasse, int numero) {
        this(0, username, motDePasse);
        this.numero = numero;
    }

    /* getters & setters */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public String getMotDePasse() { return motDePasse; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public List<Animal> getAnimaux() { return animaux; }
    public boolean aDesAnimaux() { return !animaux.isEmpty(); }

    public int getNbPartiesGagnees() { return nbPartiesGagnees; }
    public int getNbPartiesPerdues() { return nbPartiesPerdues; }
    public int getEgalites() { return egalites; }

    public void ajouterAnimal(Animal a) { animaux.add(a); }
    public void supprimerAnimal(Animal a) { animaux.remove(a); }
    public void ajouterPartieGagnee() { nbPartiesGagnees++; }
    public void ajouterPartiePerdue() { nbPartiesPerdues++; }
    public void ajouterEgalite() { egalites++; }

    @Override
    public String toString() {
        return "Joueur " + numero + " (id=" + id + ") : " + username +
               " [Gagnées:" + nbPartiesGagnees +
               " Perdues:" + nbPartiesPerdues +
               " Égalités:" + egalites + "]";
    }

    public List<Animal> getAnimauxSurPlateau(Plateau plateau) {
        List<Animal> res = new ArrayList<>();
        for (Animal a : plateau.getAnimauxSurPlateau()) {
            if (a.getProprietaire() == this) {
                res.add(a);
            }
        }
        return res;
    }
}
