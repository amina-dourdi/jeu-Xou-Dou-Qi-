package console;

import modele.*;
import Regle.*;

import java.util.List;
import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans le jeu Xou Dou Qi !");

        // 1. Créer les joueurs
        Joueur joueur1 = new Joueur("Alice", "motdepasse1", 1);
        Joueur joueur2 = new Joueur("Bob", "motdepasse2", 2);

        // 2. Créer et initialiser le plateau
        Plateau plateau = new Plateau();
        plateau.placerAnimaux(joueur1, joueur2);

        // 3. Initialiser la partie
        Joueur joueurActuel = joueur1;
        boolean partieFinie = false;

        while (!partieFinie) {
            System.out.println("\nPlateau actuel :");
            plateau.afficher();

            System.out.println("\n" + joueurActuel.getUsername() + ", voici tes animaux en jeu :");
            List<Animal> animaux = joueurActuel.getAnimauxSurPlateau(plateau);
            if (animaux.isEmpty()) {
                System.out.println("❌ Tu n'as plus d'animaux en jeu !");
                // Joueur a perdu
                partieFinie = true;
                Joueur gagnant = (joueurActuel == joueur1) ? joueur2 : joueur1;
                afficherResultat(gagnant, joueurActuel, plateau);
                break;
            }

            int index = 1;
            for (Animal a : animaux) {
                System.out.println(index + ". " + Case.abrevAnimal(a.getNom()) + " à la position " + a.getPosition());
                index++;
            }

            System.out.print("\nQuel animal veux-tu déplacer ? (numéro) : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            if (choix < 1 || choix > animaux.size()) {
                System.out.println("❌ Choix invalide, essaie encore.");
                continue;
            }

            Animal animalChoisi = animaux.get(choix - 1);
            System.out.println("Tu as choisi de déplacer le " + animalChoisi.getNom() + " à " + animalChoisi.getPosition());

            System.out.print("Ligne d’arrivée (0-8) : ");
            int xArrivee = scanner.nextInt();
            System.out.print("Colonne d’arrivée (0-6) : ");
            int yArrivee = scanner.nextInt();
            scanner.nextLine();

            Position posArrivee = new Position(xArrivee, yArrivee);

            boolean valide = GestionDeplacement.deplacementValide(animalChoisi, posArrivee, plateau);

            if (valide) {
                Position ancienne = animalChoisi.getPosition();
                plateau.getCase(posArrivee).setAnimal(animalChoisi);
                plateau.getCase(ancienne).setAnimal(null);
                animalChoisi.setPosition(posArrivee);
                System.out.println("✅ Déplacement réussi !");
            } else {
                System.out.println("❌ Déplacement invalide !");
                continue; // rejouer le tour sans changer de joueur
            }

            // Vérifier victoire ou défaite
            if (GestionVictoire.estVictoire(joueurActuel, plateau)) {
                partieFinie = true;
                afficherResultat(joueurActuel, (joueurActuel == joueur1 ? joueur2 : joueur1), plateau);
            } else if (GestionVictoire.estPerdu(joueurActuel == joueur1 ? joueur2 : joueur1, plateau)) {
                partieFinie = true;
                afficherResultat(joueurActuel, (joueurActuel == joueur1 ? joueur2 : joueur1), plateau);
            } else {
                // Changer de joueur pour le tour suivant
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
        }

        scanner.close();
    }
	
	private static void afficherResultat(Joueur gagnant, Joueur perdant, Plateau plateau) {
        System.out.println("\n🎉 " + gagnant.getUsername() + " a gagné la partie !");
        System.out.println(gagnant.getUsername() + " score: " + GestionVictoire.calculerScore(gagnant, plateau));
        System.out.println(perdant.getUsername() + " score: " + GestionVictoire.calculerScore(perdant, plateau));
    }
}
