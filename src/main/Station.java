package main;

import java.util.ArrayList;

/**
 * Станції
 */
public class Station implements Location {
    String name; // Ім’я станції
    Cords position; // Координати розміщення станції
    ArrayList<String> storage; // Перелік потягів на станції

    public Station (String name, Cords position, ArrayList<String> trains){
        this.name = name;
        this.position = position;
        // ініціювання пустих this.storage
        for(String t : trains) {
            this.storage.add(t);
        }
        // out: відображення стану "готує до відправки"
    }

    boolean storageEmpty() {
        return (storage.size() > 0) ? true : false;
    }

    // Перевірка приїзду потяга
    void checkNew(ArrayList<Train> ts) {
        for(Train t : ts) {
            if ((Cords.compare(this.position, t.position)) // якщо потяг на станції
                &&(!isInStorage(t.name))) { // якщо потяг ще не доданий у склад
                this.trainArrive(t);
            }
        }
    }

    // Запис про приїзд потяга
    void trainArrive (Train train) {
        // out: міняє стан на "Приймає потяг T"
        this.storage.add(train.name);
        train.nextDestination();
    }

    // Підготовка до відправлення потяга
    void checkStorage(ArrayList<Train> ts, ArrayList<Road> rs) {
        if (!this.storageEmpty()) { // якщо є потяги на станції
            for(String tString : storage) {
                for(Train t : ts) {
                    if (t.name == tString) {
                        // перевірка маршруту
                        Road current = this.checkRoads(t.getLastDestination(), rs, ts);
                        if (current != null) {
                            this.storage.remove (t.name); // виїжджає із станції
                            t.location = current; // ставить потяг на дорогу
                            current.trains.add(t.name); // на дорозі потяг
                        } else {
                            // out: стан станції "потяг затримується"
                        }
                    }
                }
            }
        } else {
            // out: стан станції " <Пусто>"
        }
    }

        // Перевірка маршруту
	/* S1↔S2:
	R12
	R2p i R1p
	S1↔S3:
	R13
	R2p i R3p
S1↔S2:
	R2p i R3p */
    Road checkRoads(Station stationTo, ArrayList<Road> rs, ArrayList<Train> ts) {
            // S1↔S2
        if ((this == Core.s1)&&(stationTo == Core.s2) || (this == Core.s2)&&(stationTo == Core.s1)) {
                if (!rs.get(0).trainsEmpty()) { // Якщо R12 дорога не пуста
                    for (String tString : rs.get(0).trains)
                        for (Train t : ts)
                            if (tString == t.name) { // знаходимо потяг
                                if (t.route.get(t.destinationIndex) == stationTo)
                                    return rs.get(0); // (return 1)
                            }
                } else
                    return rs.get(0); // R12
                // Якщо вільні дороги R1p & R2p
                if ((rs.get(2).trainsEmpty())&&(rs.get(3).trainsEmpty())) {
                    if (this.name == "S1")
                        return rs.get(2); // R1p
                    else
                        return rs.get(3); // R2p
                }// else if // якщо R1p зайнята, а R2p вільна, то
                // (3)

                //else if // якщо  R2p зайнята, а R1p вільна, то (3)
                //else // чи взагалі потрібно розглядати варіант,
                // коли обидві дороги зайняті, бо тоді потрібно
                // перевіряти напрями потягу в один бік
        } //else if // перевірка S1↔S3
            // то аналогічно попередньому
            // else // аналогічно попередньому S2↔S3
             // повертаємо null – дороги зайняті
        return null;
    }
    private boolean isInStorage(String t) {
        for(String storageT : storage) {
            if (storageT.equals(t))
                return true;
        }
        return false;
    }
}
