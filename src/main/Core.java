package main;

import java.util.ArrayList;

/**
 * Created by Light on 27.11.2014.
 */
public class Core {
    public static Station s1, s2, s3;
    public static Train t1, t2, t3;
    public static Switch p;
    public static Road r1, r2, r3; // .........

    public static Station[] getAllS() {
        return new Station[] {s1, s2, s3};
    }

    public static Train[] getAllT() {
        return new Train[] {t1, t2, t3};
    }

    public static Road[] getAllR() {
        return new Road[] {r1, r2, r3};
    }

    public static Train[] getTrainsOnRoad(Road r) {
        ArrayList<Train> ts = new ArrayList<Train>(3);
        for(Train t : getAllT()) {
            if (t.location.equals(r)) {
                ts.add(t);
            }
        }
        return (Train[])ts.toArray();
    }
}
