package modele;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
	public static final int NB_LIGNES = 9;
    public static final int NB_COLONNES = 7;

    private Case[][] grille;

    public Plateau() {
        grille = new Case[NB_LIGNES][NB_COLONNES];
        initialiserCases();
    }

    private void initialiserCases() {
        for (int i = 0; i < NB_LIGNES; i++) {
            for (int j = 0; j < NB_COLONNES; j++) {
                grille[i][j] = new Case(new Position(i, j), TypeCase.NORMALE);
            }
        }

        //Eau
        for (int i = 3; i <= 5; i++) {
            grille[i][1].setType(TypeCase.EAU);
            grille[i][2].setType(TypeCase.EAU);
            grille[i][4].setType(TypeCase.EAU);
            grille[i][5].setType(TypeCase.EAU);
        }

        //Pieges
        grille[0][2].setType(TypeCase.PIEGE);
        grille[0][4].setType(TypeCase.PIEGE);
        grille[1][3].setType(TypeCase.PIEGE);

        grille[8][2].setType(TypeCase.PIEGE);
        grille[8][4].setType(TypeCase.PIEGE);
        grille[7][3].setType(TypeCase.PIEGE);

        //Sanctuaires
        grille[0][3].setType(TypeCase.SANCTUAIRE);
        grille[8][3].setType(TypeCase.SANCTUAIRE);
    }

    public void placerAnimaux(Joueur j1, Joueur j2) {
        grille[7][1].setAnimal(new Chat(j1, new Position(7, 1)));
        grille[7][5].setAnimal(new Chien(j1, new Position(7, 5)));
        grille[6][6].setAnimal(new Rat(j1, new Position(6, 6)));
        grille[6][4].setAnimal(new Panthere(j1, new Position(6, 4)));
        grille[6][0].setAnimal(new Elephant(j1, new Position(6, 0)));
        grille[8][0].setAnimal(new Tigre(j1, new Position(8, 0)));
        grille[8][6].setAnimal(new Lion(j1, new Position(8, 6)));
        grille[6][2].setAnimal(new Loup(j1, new Position(6, 2)));

        grille[1][5].setAnimal(new Chat(j2, new Position(1, 5)));
        grille[1][1].setAnimal(new Chien(j2, new Position(1, 1)));
        grille[2][0].setAnimal(new Rat(j2, new Position(2, 0)));
        grille[2][2].setAnimal(new Panthere(j2, new Position(2, 2)));
        grille[2][6].setAnimal(new Elephant(j2, new Position(2, 6)));
        grille[0][6].setAnimal(new Tigre(j2, new Position(0, 6)));
        grille[0][0].setAnimal(new Lion(j2, new Position(0, 0)));
        grille[2][4].setAnimal(new Loup(j2, new Position(2, 4)));
    }

    public Case getCase(Position pos) {
        if (pos.estValide()) {
            return grille[pos.getX()][pos.getY()];
        }
        return null;
    }

    public void afficher() {
        System.out.println("    0    1    2    3    4    5    6");
        for (int i = 0; i < NB_LIGNES; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < NB_COLONNES; j++) {
                Case c = grille[i][j];
                System.out.print(c.toString());
            }
            System.out.println();
        }
    }
    
    public boolean peutSauterRiviere(Position from, Position to) {
        // Vérifier que from et to sont sur même ligne ou colonne
        if (!from.estSurLigneDroite(to)) return false;

        if (from.getX() == to.getX()) {
            // même ligne, vérifier colonnes entre from.y et to.y
            int yStart = Math.min(from.getY(), to.getY()) + 1;
            int yEnd = Math.max(from.getY(), to.getY()) - 1;

            for (int y = yStart; y <= yEnd; y++) {
                Case c = getCase(new Position(from.getX(), y));
                if (c == null || !c.estEau() || !c.estVide()) {
                    return false;
                }
            }
            return true;
        } else if (from.getY() == to.getY()) {
            // même colonne, vérifier lignes entre from.x et to.x
            int xStart = Math.min(from.getX(), to.getX()) + 1;
            int xEnd = Math.max(from.getX(), to.getX()) - 1;

            for (int x = xStart; x <= xEnd; x++) {
                Case c = getCase(new Position(x, from.getY()));
                if (c == null || !c.estEau() || !c.estVide()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public List<Animal> getAnimauxSurPlateau() {
        List<Animal> animaux = new ArrayList<>();
        for (int i = 0; i < NB_LIGNES; i++) {
            for (int j = 0; j < NB_COLONNES; j++) {
                Animal a = grille[i][j].getAnimal();
                if (a != null) {
                    animaux.add(a);
                }
            }
        }
        return animaux;
    }

}
