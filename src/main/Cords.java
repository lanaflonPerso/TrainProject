package main;

/**
 * Coordinates class
 */
public class Cords {
    public int x;
    public int y;

    public Cords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean compare(Cords c1, Cords c2) {
      return (c1.x == c2.x && c1.y == c2.y) ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cords cords = (Cords) o;

        if (x != cords.x) return false;
        if (y != cords.y) return false;

        return true;
    }

    @Override
    public String toString() {
        return x + "; " + y;
    }
}