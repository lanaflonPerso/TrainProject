package main;

import java.util.ArrayList;

class Barrier {
    String name; // Ім’я шлагбаума
    boolean enable; // Вкл./Викл.
    ArrayList<Cords> position; // Координати розміщення датчиків шлагбаума

    public Barrier(String name, ArrayList<Cords> position){
    this.name = name;
    this.enable = true;
    // ініціювання пустих this.position
    for(Cords p : position) { this.position.add(p);}
    // out: малює відкритий шлагбаум і стан “відкритий”
}
    /* Якщо треба прописати функції AddPosition, якщо в джаві не передбачені Add-и */
    // Змінна стану шлагбаума
    void ChangeStage() {
        this.enable = (this.enable) ? false: true;
        // out: змінює стан шлагбаума та перемальовує
    }
}
