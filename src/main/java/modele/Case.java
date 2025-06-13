package modele;

public class Case {
	private Position position;
    private TypeCase type;
    private Animal animal;

    public Case(Position position, TypeCase type) {
        this.position = position;
        this.type = type;
        this.animal = null;
    }

    // Getters et Setters
    public Position getPosition() {
        return position;
    }

    public TypeCase getType() {
        return type;
    }

    public void setType(TypeCase type) {
        this.type = type;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public boolean estVide() {
        return animal == null;
    }

    public boolean estEau() {
        return type == TypeCase.EAU;
    }

    public boolean estPiege() {
        return type == TypeCase.PIEGE;
    }

    public boolean estSanctuaire() {
        return type == TypeCase.SANCTUAIRE;
    }

    @Override
    public String toString() {
        if (animal != null) {
        	return "[" + animal.toString() + "]";
        } else if (estEau()) {
            return "[~~~]";
        } else if (estPiege()) {
            return "[***]";
        } else if (estSanctuaire()) {
            return "[###]";
        } else {
            return "[   ]";
        }
    }

    public static String abrevAnimal(String nom) {
        switch (nom) {
            case "Lion":
                return "Ln";
            case "Loup":
                return "Lp";
            case "Tigre":
                return "Tg";
            case "Chat":
                return "Ct";
            case "Chien":
                return "Cn";
            case "Panthère":
                return "Ph";
            case "Éléphant":
                return "El";
            case "Rat":
                return "Rt";
            default:
                return nom.substring(0, 2); // fallback
        }
    }
}
