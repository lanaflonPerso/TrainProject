package main;

/**
 * Світлофори
 */
public class Light {
    String name; // Ім’я світлофора
    public boolean enable; // Вкл./Викл.
    Cords position; // Координати розміщення світлофора

    public Light (String name, Cords position) {
        this.name = name;
        this.enable = true;
        this.position = position;
        // out: малює світлофор і стан “зелений”
    }

    @Override
    public String toString() {
        return name;
    }
}
