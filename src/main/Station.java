package main;

import java.io.IOException;

/**
 * Станції
 */
public class Station implements Location {
    String name; // Ім’я станції
    Cords position; // Координати розміщення станції
    public String state; // стан на панелі станів
    public Train[] storage; // Перелік потягів на станції
    public int storageSize; // Кількість потягів на станції

    public Station(String name, Cords position){
        this.name = name;
        this.position = position;
        storage = new Train[3];
        // ініціювання пустих this.storage
        this.state = "---";
    }

    boolean storageEmpty() {
        return (storageSize == 0) ? true : false;
    }

    /**
     * Перевірка приїзду потяга
     */
    public void checkNewTrains() throws IOException {
        for(Train t : Core.getAllT()) {
            if (Cords.compare(this.position, t.position) // якщо потяг на станції
                    && !isInStorage(t) && !t.action) { // якщо потяг ще не доданий у склад (!t.action - не чіпати тих, які готові до відправки наступної ітерації)
                this.trainArrived(t);
            }
        }
    }

    /**
     * Запис про приїзд потяга
     */
    public void trainArrived(Train t) throws IOException {

        this.state =  "приймає " + t;
        /*System.out.println(this + " приймає потяг " + t);
        System.out.println(t + " випускає пасажирів на " + this);*/
        Core.log.write(this + " приймає потяг " + t + "\n");
        Core.log.write(t + " випускає пасажирів на " + this + "\n");
        storage[storageSize++] = t;
        t.setNextDestination();
        if (t.location == null) { // не збільшувати індекс, якщо це етап ініціалізації
            t.destinationIndex--;
        }
    }

    /**
     * Підготовка до відправлення потяга
     */
    public void releaseTrainsFromStorage() throws IOException {
        if (!this.storageEmpty()) { // якщо є потяги на станції
            for (int i = 0; i < storageSize; i++) {
                Train t = storage[i];
                // перевірка маршруту
                Road nextRoad = checkRoads(t.getNextDestination());
                if (nextRoad != null) {
                    System.out.println("Next dest = " + t.getNextDestination());
                    // дозволяє при наступній ітерації випустити потяг
                    storage[i] = null;
                    storageSize--;
                    System.out.println(t + " набирає пасажирів на " + this);
                    Core.log.write(t + " набирає пасажирів на " + this + "\n");
                    this.state =  "відправляє " + t;

                    t.location = nextRoad; // ставить потяг на дорогу
                    t.action = true;
                    System.out.println("ROAD CHANGED TO " + nextRoad);
                } else {
                    System.out.println(t + " затримується з відправкою");
                    Core.log.write(t + " затримується з відправкою" + "\n");
                }
            }
        }
    }

 /*
    S1↔S2: R12; R2p i R1p
	S1↔S3:	R13; R2p i R3p
    S2↔S3:	R2p i R3p
 */

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

    /**
     * Перевірка чи знаходиться потяг на станції.
     * @param t потяг
     */
    private boolean isInStorage(Train t) {
        for(Train storageT : storage) {
            if (t.equals(storageT))
                return true;
        }
        return false;
    }

    /**
     * Повертає потрібну потягу дорогу, або ж null, якщо дороги зайняті.
     * @param stationTo на яку станцію прямує потяг
     * @return null – дороги зайняті
     */
    private Road getRoad(Road rHead, Road r1p, Road r2p, Station stationTo) {
        if (rHead != null && !rHead.isEmpty()) { // Якщо дорога не пуста
            if (checkSafeMovementOnRoad(rHead))
                return rHead;
        } else if (rHead != null) {
            return rHead; // головна дорога
        }
        // Якщо вільні 3 допоміжних дороги
        if ((Core.r1p.isEmpty())&&(Core.r2p.isEmpty())&&(Core.r3p.isEmpty())) {
            if (this == Core.s1 || (this == Core.s2 && rHead == null)) // або це станція s2 за умови, що дорога - між S2-S3
                return r1p; // R1p
            else
                return r2p; // R2p
        } else { // або на першій, або на другій дорозі (або і там, і там) точно є потяг (або на третій)
            for (Train t : Core.getTrainsOnRoad(Core.r1p)) {
                // потяг прямує до станції, на якій знаходиться даний
                if (t.getNextDestination() == this)
                    return null; // дороги зайняті
            }
            // аналогічно для другої дороги
            for (Train t : Core.getTrainsOnRoad(Core.r2p)) {
                if (t.getNextDestination() == this)
                    return null;
            }
            // аналогічно для третьої дороги
            for (Train t : Core.getTrainsOnRoad(Core.r3p)) {
                if (t.getNextDestination() == this)
                    return null;
            }
            // жодний потяг не їде назустріч даному, можна вирушати
            if (this == Core.s1 || (this == Core.s2 && rHead == null)) { // або це станція s2 за умови, що дорога - між S2-S3
                if (checkSafeMovementOnRoad(r1p))
                    return r1p;
            }
            else {
                if (checkSafeMovementOnRoad(r2p))
                    return r2p;
            }
            return null; // дистанція не дотримана, залишаємо потяг на станції
        }
    }

    /**
     * Перевірка безпечності руху по дорозі.
     * Тримаємо дистанцію 2 клітинки, перевіряємо наявність потягів, що рухаються назустріч.
     * @param r дорога, на якій відбувається перевірка
     * @return true - якщо дистанція в 2 клітинки дотримується, false - якщо ні
     */
    private boolean checkSafeMovementOnRoad(Road r) {
        if (!r.isEmpty()) { // якщо дорога не пуста
            for (Train t : Core.getTrainsOnRoad(r)) {
                // потяг не їде на дану станцію, а їде з неї
                if (t.getNextDestination() != this) {
                    int stationIndex = r.way.indexOf(this.position);
                    int trainIndex   = r.way.indexOf(t.position);
                    if (trainIndex - stationIndex > 2 || trainIndex - stationIndex < -2) // тримаємо дистанцію в 2 клітинки
                        return true; // можна їхати за ним
                    else
                        return false; // дистанція не дотримана
                } else {
                    return false; // дорога зайнята, на станцію рухається потяг
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
