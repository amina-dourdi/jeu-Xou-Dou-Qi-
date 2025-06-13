package modele;

import java.time.LocalDateTime;

public class Partie {
	private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;
    private Joueur joueurActuel;
    private Joueur gagnant;
    private LocalDateTime datePartie;

    public Partie(Joueur j1, Joueur j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.plateau = new Plateau();
        this.joueurActuel = j1;
        this.datePartie = LocalDateTime.now();
    }
}
