package main;

/**
 * Станції
 */
public class Station implements Location {
    String name; // Ім’я станції
    Cords position; // Координати розміщення станції
    public Train[] storage; // Перелік потягів на станції
    public int storageSize; // Кількість потягів на станції

    public Station(String name, Cords position, Train[] trains){
        this.name = name;
        this.position = position;
        storage = new Train[3];
        // ініціювання пустих this.storage
        int i = 0;
        for(Train t : trains) {
            this.storage[i++] = t;
        }
        storageSize = i;
        // out: відображення стану "готує до відправки"
    }

    boolean storageEmpty() {
        return (storageSize == 0) ? true : false;
    }

    // Перевірка приїзду потяга
    public void checkNew() {
        for(Train t : Core.getAllT()) {
            if ((Cords.compare(this.position, t.position)) // якщо потяг на станції
                    &&(!isInStorage(t))) { // якщо потяг ще не доданий у склад
                this.trainArrived(t);
            }
        }
    }

    // Запис про приїзд потяга
    public void trainArrived(Train train) {
        System.out.println("=========== Arrived " + train);
        // out: міняє стан на "Приймає потяг T"
        storage[storageSize++] = train;
        train.setNextDestination();
    }

    // Підготовка до відправлення потяга
    public void checkStorage() {
        if (!this.storageEmpty()) { // якщо є потяги на станції
            for (int i = 0; i < storageSize; i++) {
                System.out.println("*** " + this + ": " + storage[i]);
                Train t = storage[i];
                // перевірка маршруту
                Road currentRoad = checkRoads(t.getNextDestination());
                if (currentRoad != null) {
                    // виїжджає зі станції
                    storage[i] = null;
                    storageSize--;
                    System.out.println(t + " deleted from " + this);

                    t.location = currentRoad; // ставить потяг на дорогу
                    t.action = true;
                    System.out.println("CHAAAAAAAAAAAAAAAAANGED!");
                } else {
                    // out: стан станції "потяг затримується" з відправкою
                }
            }
        } else {
            // out: стан станції " <Пусто>"
        }
    }

 /* S1↔S2: R12; R2p i R1p
	S1↔S3:	R13; R2p i R3p
    S2↔S3:	R2p i R3p */

    /**
     * @return дорога, потрібна для руху потяга, або null, якщо дороги зайняті
     */
    Road checkRoads(Station stationTo) {
        // S1↔S2
        if ((this == Core.s1)&&(stationTo == Core.s2) || (this == Core.s2)&&(stationTo == Core.s1)) {
            return getRoad(Core.r12, Core.r1p, Core.r2p, stationTo);
        } else if((this == Core.s1)&&(stationTo == Core.s3) || (this == Core.s3)&&(stationTo == Core.s1)) { // S1↔S3
            return getRoad(Core.r13, Core.r1p, Core.r3p, stationTo);
        } else { // S2↔S3
            return getRoad(null, Core.r2p, Core.r3p, stationTo);
        }
    }

    private boolean isInStorage(Train t) {
        for(Train storageT : storage) {
            System.out.println(storageT);
            if (t.equals(storageT))
                return true;
        }
        return false;
    }

    /**
     * Повертає потрібну потягу дорогу, або ж null, якщо дороги зайняті
     * @param stationTo на яку станцію прямує потяг
     * @return null – дороги зайняті
     */
    private Road getRoad(Road rHead, Road r1p, Road r2p, Station stationTo) {
        if (rHead != null && !rHead.isEmpty()) { // Якщо головна дорога не пуста
            for (Train t : Core.getTrainsOnRoad(rHead)) {
                // потяг теж прямує до цієї ж станції, можна поїхати за ним
                if (t.getNextDestination() == stationTo)
                    return rHead;
            }
        } else {
            return rHead; // головна дорога
        }
        // Якщо вільні дві допоміжних дороги
        if ((r1p.isEmpty())&&(r2p.isEmpty())) {
            if (this == Core.s1 || (this == Core.s2 && rHead == null)) // або це станція s2 за умови, що дорога - між S2-S3
                return r1p; // R1p
            else
                return r2p; // R2p
        } else { // або на першій, або на другій дорозі (або і там, і там) точно є потяг
            for (Train t : Core.getTrainsOnRoad(r1p)) {
                // потяг прямує до станції, на якій знаходиться даний
                if (t.getNextDestination() == this)
                    return null; // дороги зайняті
            }
            // аналогічно для другої дороги
            for (Train t : Core.getTrainsOnRoad(r2p)) {
                if (t.getNextDestination() == this)
                    return null;
            }
            // жодний потяг не їде назустріч даному, можна вирушати
            if (this == Core.s1 || (this == Core.s2 && rHead == null)) // або це станція s2 за умови, що дорога - між S2-S3
                return r1p; // R1p
            else
                return r2p; // R2p
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
