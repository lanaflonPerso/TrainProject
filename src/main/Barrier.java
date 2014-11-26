package main;

import java.util.ArrayList;

public class Barrier {
    String name; // Ім’я шлагбаума
    boolean enable; // Вкл./Викл.
    ArrayList<Cords> position; // Координати розміщення датчиків шлагбаума

    public Barrier(String name, ArrayList<Cords> position){
        this.name = name;
        this.enable = true;
        position = new ArrayList<Cords>();
        for(Cords p : position) { this.position.add(p);}
        // out: малює відкритий шлагбаум і стан “відкритий”
    }

    // Змінна стану шлагбаума
    public void changeStage() {
        this.enable = !this.enable;
        // out: змінює стан шлагбаума та перемальовує
    }
}
