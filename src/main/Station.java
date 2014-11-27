package main;

import java.util.ArrayList;

/**
 * Станції
 */
public class Station implements Location {
    String name; // Ім’я станції
    Cords position; // Координати розміщення станції
    ArrayList<Train> storage; // Перелік потягів на станції

    public Station(String name, Cords position, Train[] trains){
        this.name = name;
        this.position = position;
        // ініціювання пустих this.storage
        for(Train t : trains) {
            this.storage.add(t);
        }
        // out: відображення стану "готує до відправки"
    }

    boolean storageEmpty() {
        return (storage.size() > 0) ? true : false;
    }

    // Перевірка приїзду потяга
    void checkNew() {
        for(Train t : Core.getAllT()) {
            if ((Cords.compare(this.position, t.position)) // якщо потяг на станції
                &&(!isInStorage(t))) { // якщо потяг ще не доданий у склад
                this.trainArrive(t);
            }
        }
    }

    // Запис про приїзд потяга
    void trainArrive (Train train) {
        // out: міняє стан на "Приймає потяг T"
        this.storage.add(train);
        train.nextDestination();
    }

    // Підготовка до відправлення потяга
    void checkStorage() {
        Train[] ts = Core.getAllT();
        Road[] rs  = Core.getAllR();
        if (!this.storageEmpty()) { // якщо є потяги на станції
            for(Train t : storage) {
                // перевірка маршруту
                Road currentRoad = checkRoads(t.getLastDestination());
                if (currentRoad != null) {
                    this.storage.remove (t.name); // виїжджає із станції
                    t.location = currentRoad; // ставить потяг на дорогу
                } else {
                    // out: стан станції "потяг затримується"
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
    Road checkRoads(Station stationTo) {
        Road[]  rs = Core.getAllR();
        Train[] ts = Core.getAllT();
            // S1↔S2
        if ((this == Core.s1)&&(stationTo == Core.s2) || (this == Core.s2)&&(stationTo == Core.s1)) {
                if (!rs[0].isEmpty()) { // Якщо R12 дорога не пуста
                    for (Train t : Core.getTrainsOnRoad(rs[0])) {
                        if (t.getLastDestination() == stationTo)
                            return rs[0]; // (return 1)
                    }
                } else {
                    return rs[0]; // R12
                }
                // Якщо вільні дороги R1p & R2p
                if ((rs[2].isEmpty())&&(rs[3].isEmpty())) {
                    if (this == Core.s1)
                        return rs[2]; // R1p
                    else
                        return rs[3]; // R2p
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
    private boolean isInStorage(Train t) {
        for(Train storageT : storage) {
            if (storageT.equals(t))
                return true;
        }
        return false;
    }
}
