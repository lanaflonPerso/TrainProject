package main;

/**
 * Coordinates class
 */
public class Cords {
    int x;
    int y;

    public Cords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean compare(Cords c1, Cords c2) {
      return (c1.x == c2.x && c1.y == c2.y) ? true : false;
    }
}