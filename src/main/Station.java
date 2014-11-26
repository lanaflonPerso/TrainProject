package main;

import java.util.ArrayList;

/**
 * Станції
 */
class Station {
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
        // Перевірка наявності елементів в this.storage
        // return true; якщо потяги відсутні
    }

        // Перевірка приїзду потяга
        void CheckNew(ArrayList<Train> ts) {
        for(Train t : ts) {
        if ((ComparePositions(this.position, t.position)) // якщо потяг на станції
        &&(t.name is not : storage)) // якщо потяг ще не доданий у склад
        // лінь писати перевірку is not : storage
        this.TrainArrive(t);
        }
        }

        // Запис про приїзд потяга
        void TrainArrive (Train train) {
        // out: міняє стан на "Приймає потяг T"
        this.storage.add(train.name);
        train.NextDestination();
        }

        // Підготовка до відправлення потяга
        void CheckStorage (ArrayList<Train> Ts) {
            if (!this.storageEmpty) { // якщо є потяги на станції
                for(String t : storage) {
                    for(Train T : Ts) {
                        if (T.name == t) {
                            // перевірка маршруту
                            Road Current = this.CheckRoads(this.name,
                            T.route[this.destinationIndex],
                            Rs, Ts);
                            if (Current != null) {
                            this.storage.remove (T.name); // виїжджає із станції
                            T.road = Current.name; // ставить потяг на дорогу
                            Current.trains.add(T.name); // на дорозі потяг
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
        Road CheckRoads (String StationFrom, String StationTo,
        ArrayList<Road> Rs, ArrayList<Train> Ts) {
            if ((StationFrom == "S1")&&(StationTo == "S2") ||
            (StationFrom == "S2")&&(StationTo == "S1")) { // S1↔S2
                if (!Rs[0].trainsEmpty) { // Якщо R12 дорога не пуста
                    for (String t : Rs[0].trains)
                        for (Train T : Ts)
                            if (t == T.name) { // знаходимо потяг
                                if (T.route[T.destinationIndex] == StationTo)
                                return Rs[0]; // (return 1)
                            }
                } else return Rs[0]; // R12
                // Якщо вільні дороги R1p & R2p
                if ((Rs[2].trainsEmpty)&&(Rs[3].trainsEmpty)) {
                    if (StationFrom == "S1")
                        return Rs[2]; // R1p
                    else
                        return Rs[3]; // R2p
                } else if // якщо R1p зайнята, а R2p вільна, то
                // (3)

                else if // якщо  R2p зайнята, а R1p вільна, то (3)
                else // чи взагалі потрібно розглядати варіант,
                // коли обидві дороги зайняті, бо тоді потрібно
                // перевіряти напрями потягу в один бік
            } else if // перевірка S1↔S3
            // то аналогічно попередньому
            // else // аналогічно попередньому S2↔S3
            /* якщо в процесі виконання функції (всіх перевірок), то повертаємо
            null – дороги зайняті */
            return null;
        }
}
