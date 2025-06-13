package modele;

import java.time.LocalDateTime;

public class Historique {
    private int joueur1Id;
    private int joueur2Id;
    private Integer gagnantId;
    private LocalDateTime datePartie;

    public Historique(int joueur1Id, int joueur2Id, Integer gagnantId, LocalDateTime datePartie) {
        this.joueur1Id = joueur1Id;
        this.joueur2Id = joueur2Id;
        this.gagnantId = gagnantId;
        this.datePartie = datePartie;
    }

    public int getJoueur1Id() {
        return joueur1Id;
    }

    public int getJoueur2Id() {
        return joueur2Id;
    }

    public Integer getGagnantId() {
        return gagnantId;
    }

    public LocalDateTime getDatePartie() {
        return datePartie;
    }
}