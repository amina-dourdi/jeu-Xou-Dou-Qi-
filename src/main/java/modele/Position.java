package modele;

public class Position {
	private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean estValide() {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }
    
    public boolean estAdjacente(Position autre) {
        int dx = Math.abs(this.x - autre.x);
        int dy = Math.abs(this.y - autre.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
    
    public boolean estSurLigneDroite(Position autre) {
        return this.x == autre.x || this.y == autre.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        Position autre = (Position) obj;
        return this.x == autre.x && this.y == autre.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
