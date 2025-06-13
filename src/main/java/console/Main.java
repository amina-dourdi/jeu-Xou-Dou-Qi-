package console;

import modele.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Regle.*;
import database.*;


public class Main {
    private static HistoriqueDAO historiqueDAO = new HistoriqueDAO();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans Xou Dou Qi\n");
        Joueur joueur1 = null;
        Joueur joueur2 = null;
        boolean quit = false;

        while (!quit) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Se connecter / Créer un compte");
            System.out.println("2. Afficher historique des parties");
            System.out.println("3. Lancer une partie");
            System.out.println("4. Quitter");
            System.out.print("> ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    joueur1 = gestionCompte(scanner, 1);
                    joueur2 = gestionCompte(scanner, 2);
                    break;
                case "2":
                    afficherHistorique();
                    break;
                case "3":
                    if (joueur1 == null || joueur2 == null) {
                        System.out.println("❌ Veuillez d'abord vous connecter ou créer les comptes.");
                    } else {
                        lancerPartie(joueur1, joueur2, scanner);
                    }
                    break;
                case "4":
                    quit = true;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
        scanner.close();
    }

    private static void afficherHistorique() {
        List<Historique> liste = historiqueDAO.listerHistorique();
        if (liste.isEmpty()) {
            System.out.println("Aucune partie enregistrée.");
            return;
        }
        int victoiresJ1 = 0, victoiresJ2 = 0, egalites = 0;
        for (Historique h : liste) {
            Integer g = h.getGagnantId();
            if (g == null) {
                egalites++;
            } else if (g.equals(h.getJoueur1Id())) {
                victoiresJ1++;
            } else {
                victoiresJ2++;
            }
        }
        System.out.println("\n=== Statistiques globales ===");
        System.out.println("Total de parties : " + liste.size());
        System.out.println("Victoires joueur 1 : " + victoiresJ1);
        System.out.println("Victoires joueur 2 : " + victoiresJ2);
        System.out.println("Égalités : " + egalites);

        System.out.println("\n=== Détail des parties ===");
        for (Historique h : liste) {
            String resultat = (h.getGagnantId() == null)
                ? "Égalité"
                : "Gagnant: Joueur " + h.getGagnantId();
            System.out.printf("%s - Joueur %d vs Joueur %d -> %s%n",
                h.getDatePartie(),
                h.getJoueur1Id(),
                h.getJoueur2Id(),
                resultat);
        }
    }

    private static void lancerPartie(Joueur joueur1, Joueur joueur2, Scanner scanner) {
        System.out.println("\nLa partie commence : " + joueur1.getUsername() + " vs " + joueur2.getUsername());
        Plateau plateau = new Plateau();
        plateau.placerAnimaux(joueur1, joueur2);

        Joueur joueurActuel = joueur1;
        boolean partieFinie = false;

        while (!partieFinie) {
            System.out.println("\nPlateau actuel :");
            plateau.afficher();

            System.out.println("\n" + joueurActuel.getUsername() + ", voici tes animaux en jeu :");
            List<Animal> animaux = joueurActuel.getAnimauxSurPlateau(plateau);
            if (animaux.isEmpty()) {
                partieFinie = true;
                Joueur gagnant = (joueurActuel == joueur1) ? joueur2 : joueur1;
                afficherResultat(gagnant, joueurActuel, plateau);
                historiqueDAO.enregistrerPartie(joueur1, joueur2, gagnant, LocalDateTime.now());
                break;
            }

            int index = 1;
            for (Animal a : animaux) {
                System.out.println(index + ". " + Case.abrevAnimal(a.getNom()) + " à la position " + a.getPosition());
                index++;
            }

            System.out.print("\nQuel animal veux-tu déplacer ? (numéro) : ");
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix < 1 || choix > animaux.size()) {
                System.out.println("❌ Choix invalide, essaie encore.");
                continue;
            }
            Animal animalChoisi = animaux.get(choix - 1);
            System.out.println("Tu as choisi de déplacer le " + animalChoisi.getNom() + " à " + animalChoisi.getPosition());

            System.out.print("Ligne d’arrivée (0-8) : ");
            int xArrivee = Integer.parseInt(scanner.nextLine());
            System.out.print("Colonne d’arrivée (0-6) : ");
            int yArrivee = Integer.parseInt(scanner.nextLine());
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
                continue;
            }

            if (GestionVictoire.estVictoire(joueurActuel, plateau)) {
                partieFinie = true;
                Joueur gagnant = joueurActuel;
                Joueur perdant = (joueurActuel == joueur1) ? joueur2 : joueur1;
                afficherResultat(gagnant, perdant, plateau);
                historiqueDAO.enregistrerPartie(joueur1, joueur2, gagnant, LocalDateTime.now());
            } else if (GestionVictoire.estPerdu(joueurActuel == joueur1 ? joueur2 : joueur1, plateau)) {
                partieFinie = true;
                Joueur gagnant = joueurActuel;
                Joueur perdant = (joueurActuel == joueur1) ? joueur2 : joueur1;
                afficherResultat(gagnant, perdant, plateau);
                historiqueDAO.enregistrerPartie(joueur1, joueur2, gagnant, LocalDateTime.now());
            } else {
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
        }
    }

    private static Joueur gestionCompte(Scanner scanner, int numeroJoueur) {
        JoueurDAO dao = new JoueurDAO();
        Joueur joueur = null;
        System.out.println("[Joueur " + numeroJoueur + "] : Veuillez vous connecter ou créer un compte.");
        System.out.println("1. Connexion");
        System.out.println("2. Créer un compte");
        System.out.print("> ");
        int choix = Integer.parseInt(scanner.nextLine());

        if (choix == 1) {
            while (joueur == null) {
                System.out.print("Nom d'utilisateur : ");
                String nom = scanner.nextLine();
                System.out.print("Mot de passe : ");
                String mdp = scanner.nextLine();
                joueur = dao.connecterJoueur(nom, mdp);
                if (joueur == null) System.out.println("Identifiants invalides, réessayez.");
            }
        } else {
            while (joueur == null) {
                System.out.print("Choisissez un username : ");
                String pseudo = scanner.nextLine();
                System.out.print("Choisissez un mot de passe : ");
                String mdp = scanner.nextLine();
                joueur = dao.creerJoueur(pseudo, mdp);
                if (joueur == null) System.out.println("Erreur lors de la création du compte, réessayez.");
            }
        }
        joueur.setNumero(numeroJoueur);
        return joueur;
    }

    private static void afficherResultat(Joueur gagnant, Joueur perdant, Plateau plateau) {
        System.out.println("\n🎉 " + gagnant.getUsername() + " a gagné la partie !");
        System.out.println(gagnant.getUsername() + " score: " + GestionVictoire.calculerScore(gagnant, plateau));
        System.out.println(perdant.getUsername() + " score: " + GestionVictoire.calculerScore(perdant, plateau));
    }
}
