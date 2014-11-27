package test;

import main.*;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Light on 27.11.2014.
 */
public class TrainTest {
    @BeforeClass
    public static void init() {
        // ініціація потягів
        Train t1 = new Train("T1", new Cords(1,4), null, null);
        Train t2 = new Train("T1", new Cords(3,1), null, null);
        Train t3 = new Train("T1", new Cords(17,12), null, null);
        Train[] trains = new Train[]{t1, t2, t3};

        /*T.Add(“T1”, cords(1,4), {“S2”, “S3”, “S1”, “S3”});
        T.Add(“T2”, cords(3,1), {“S1”, “S2”, “S3”, “S2”});
        T.Add(“T3”, cords(17,12), {“S3”, “S1”, “S2”, “S1”});*/

        // ініціація станцій
        Station s1 = new Station("S1", new Cords(3,1), new Train[]{t2});
        Station s2 = new Station("S2", new Cords(1,14), new Train[]{t1});
        Station s3 = new Station("S3", new Cords(17,11), new Train[]{t3});
        Station[] stations = new Station[]{s1, s2, s3};

        // ініціація перемикача
        Switch p = new Switch("P", new Cords(8,11));

        // ініціація доріг
        // Road 1
        Cords[] crArr = new Cords[] {
                new Cords(3,1), new Cords(2,1), new Cords(1,1), new Cords(1,2), new Cords(1,3), new Cords(1,4),
                new Cords(1,5), new Cords(1,6), new Cords(1,7), new Cords(1,8), new Cords(1,9), new Cords(1,10),
                new Cords(1,11), new Cords(1,12), new Cords(1,13), new Cords(1,14)
        };
        ArrayList<Cords> cr1 = (ArrayList)Arrays.asList(crArr);
        Road r1 = new Road("R12", s1, s2, cr1);

        // Road 2
        crArr = new Cords[] {
            new Cords(3,1), new Cords(4,1), new Cords(5,1), new Cords(6,1), new Cords(7,1), new Cords(8,1),
            new Cords(9,1), new Cords(10,1), new Cords(11,1), new Cords(12,1), new Cords(13,1), new Cords(13,2),
            new Cords(13,3), new Cords(13,4), new Cords(13,5), new Cords(13,6), new Cords(14,6), new Cords(14,7),
            new Cords(15,7), new Cords(15,8), new Cords(16,8), new Cords(16,9), new Cords(17,9), new Cords(17,10),
            new Cords(17,11)
        };
        ArrayList<Cords> cr2 = (ArrayList)Arrays.asList(crArr);
        Road r2 = new Road("R13", s1, s3, cr2);

        // Road 3
        crArr = new Cords[] {
                new Cords(3,1), new Cords(3,2), new Cords(3,3), new Cords(4,3), new Cords(4,4), new Cords(5,4), new Cords(5,5),
                new Cords(6,5), new Cords(6,6), new Cords(6,7), new Cords(7,7), new Cords(7,8), new Cords(8,8), new Cords(8,9),
                new Cords(8,10), new Cords(8,11)
        };
        ArrayList<Cords> cr3 = (ArrayList)Arrays.asList(crArr);
        Road r3 = new Road("R1p", s1, p, cr2);

        // Road 4
        crArr = new Cords[] {
                new Cords(1,14), new Cords(2,14), new Cords(3,14), new Cords(3,13), new Cords(4,13), new Cords(4,12),
                new Cords(5,12), new Cords(6,12), new Cords(6,11), new Cords(7,11), new Cords(8,11)
        };
        ArrayList<Cords> cr4 = (ArrayList)Arrays.asList(crArr);
        Road r4 = new Road("R2p", s2, p, cr2);

        // Road 5
        crArr = new Cords[] {
                new Cords(17,11), new Cords(16,11), new Cords(15,11), new Cords(14,11), new Cords(13,11),
                new Cords(12,11), new Cords(11,11), new Cords(10,11), new Cords(9,11), new Cords(8,11)
        };
        ArrayList<Cords> cr5 = (ArrayList)Arrays.asList(crArr);
        Road r5 = new Road("R3p", s3, p, cr2);

        Road[] roads = new Road[]{r1, r2, r3, r4, r5};

        // ініціація шлагбаумів
        // Barrier 1
        ArrayList<Cords> cb1 = new ArrayList<Cords>();
        cb1.add(new Cords(1, 7));
        cb1.add(new Cords(1, 9));
        Barrier b1 = new Barrier("B1", cb1);
        // Barrier 2
        ArrayList<Cords> cb2 = new ArrayList<Cords>();
        cb1.add(new Cords(8, 1));
        cb1.add(new Cords(10, 1));
        Barrier b2 = new Barrier("B2", cb1);
        Barrier[] barriers = new Barrier[] {b1, b2};

        // ініціація світлофорів
        Light l1 = new Light("L1", new Cords(8,10));
        Light l2 = new Light("L2", new Cords(7,11));
        Light l3 = new Light("L3", new Cords(9,11));
        Light[] ligts = new Light[] {l1, l2, l3};

        // запуск системи
//        Main(S, R, B, L, P, T);

    }
}
