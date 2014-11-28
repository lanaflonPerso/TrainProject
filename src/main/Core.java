package main;

import java.util.ArrayList;

/**
 * Created by Light on 27.11.2014.
 */
public class Core {
    public static Train t1, t2, t3;
    public static Station s1, s2, s3;
    public static Switch p;
    public static Barrier b1, b2;
    public static Light l1, l2, l3;
    public static Road r12, r13, r1p, r2p, r3p;

    public static Station[] getAllS() {
        return new Station[] {s1, s2, s3};
    }

    public static Train[] getAllT() {
        return new Train[] {t1, t2, t3};
    }

    public static Road[] getAllR() {
        return new Road[] {r12, r13, r1p, r2p, r3p};
    }

    public static Barrier[] getAllB() {
        return new Barrier[] {b1, b2};
    }

    public static Light[] getAllL() {
        return new Light[] {l1, l2, l3};
    }

    public static ArrayList<Train> getTrainsOnRoad(Road r) {
        ArrayList<Train> ts = new ArrayList<Train>(3);
        for(Train t : getAllT()) {
            if (t.location.equals(r)) {
                ts.add(t);
            }
        }
        return ts;
    }
}
