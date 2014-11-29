package test;

import main.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Light on 27.11.2014.
 */
public class TrainTest {
    static Train t1, t2, t3;
    static Station s1, s2, s3;
    static Switch p;
    static Barrier b1, b2;
    static Light l1, l2, l3;
    static Road r12, r13, r1p, r2p, r3p;

    static Train[] trains;
    static Station[] stations;
    static Road[] roads;
    public Barrier[] barriers;
    static Light[] ligts;

    @Before
    public void init() {
        // ініціація потягів
        t1 = new Train("T1", new Cords(1,14));
        t2 = new Train("T2", new Cords(3,1));
//        t3 = new Train("T3", new Cords(17,12));
        trains = new Train[]{t1, t2};
//        trains = new Train[]{t1, t2, t3};

        // ініціація станцій
//        s1 = new Station("S1", new Cords(3,1), new Train[]{t2});
//        s2 = new Station("S2", new Cords(1,14), new Train[]{t1});
//        s3 = new Station("S3", new Cords(17,11), new Train[]{t3});
//
        s1 = new Station("S1", new Cords(3,1));
        s2 = new Station("S2", new Cords(1,14));
        s3 = new Station("S3", new Cords(17,11));

        stations = new Station[]{s1, s2, s3};

        // задаємо маршрути для кожного потяга
        t1.route = new Station[] {s2, s3, s1, s3};
        t2.route = new Station[] {s1, s2, s3, s2};
//        t3.route = new Station[] {s3, s1, s2, s1};

        // ініціація перемикача
        p = new Switch("P", new Cords(8,11));

        // ініціація доріг
        // Road 1
        Cords[] crArr = new Cords[] {
                new Cords(3,1), new Cords(2,1), new Cords(1,1), new Cords(1,2), new Cords(1,3), new Cords(1,4),
                new Cords(1,5), new Cords(1,6), new Cords(1,7), new Cords(1,8), new Cords(1,9), new Cords(1,10),
                new Cords(1,11), new Cords(1,12), new Cords(1,13), new Cords(1,14)
        };
        ArrayList<Cords> cr1 = new ArrayList<Cords>(Arrays.asList(crArr));
        r12 = new Road("R12", s1, s2, cr1);

        // Road 2
        crArr = new Cords[] {
            new Cords(3,1), new Cords(4,1), new Cords(5,1), new Cords(6,1), new Cords(7,1), new Cords(8,1),
            new Cords(9,1), new Cords(10,1), new Cords(11,1), new Cords(12,1), new Cords(13,1), new Cords(13,2),
            new Cords(13,3), new Cords(13,4), new Cords(13,5), new Cords(13,6), new Cords(14,6), new Cords(14,7),
            new Cords(15,7), new Cords(15,8), new Cords(16,8), new Cords(16,9), new Cords(17,9), new Cords(17,10),
            new Cords(17,11)
        };
        ArrayList<Cords> cr2 = new ArrayList<Cords>(Arrays.asList(crArr));
        r13 = new Road("R13", s1, s3, cr2);

        // Road 3
        crArr = new Cords[] {
                new Cords(3,1), new Cords(3,2), new Cords(3,3), new Cords(4,3), new Cords(4,4), new Cords(5,4), new Cords(5,5),
                new Cords(6,5), new Cords(6,6), new Cords(6,7), new Cords(7,7), new Cords(7,8), new Cords(8,8), new Cords(8,9),
                new Cords(8,10), new Cords(8,11)
        };
        ArrayList<Cords> cr3 = new ArrayList<Cords>(Arrays.asList(crArr));
        r1p = new Road("R1p", s1, p, cr3);

        // Road 4
        crArr = new Cords[] {
                new Cords(1,14), new Cords(2,14), new Cords(3,14), new Cords(3,13), new Cords(4,13), new Cords(4,12),
                new Cords(5,12), new Cords(6,12), new Cords(6,11), new Cords(7,11), new Cords(8,11)
        };
        ArrayList<Cords> cr4 = new ArrayList<Cords>(Arrays.asList(crArr));
        r2p = new Road("R2p", s2, p, cr4);

        // Road 5
        crArr = new Cords[] {
                new Cords(17,11), new Cords(16,11), new Cords(15,11), new Cords(14,11), new Cords(13,11),
                new Cords(12,11), new Cords(11,11), new Cords(10,11), new Cords(9,11), new Cords(8,11)
        };
        ArrayList<Cords> cr5 = new ArrayList<Cords>(Arrays.asList(crArr));
        r3p = new Road("R3p", s3, p, cr5);
        roads = new Road[]{r12, r13, r1p, r2p, r3p};

        // ініціація шлагбаумів
        // Barrier 1
        ArrayList<Cords> cb1 = new ArrayList<Cords>();
        cb1.add(new Cords(1, 7));
        cb1.add(new Cords(1, 9));
        b1 = new Barrier("B1", cb1);
        // Barrier 2
        ArrayList<Cords> cb2 = new ArrayList<Cords>();
        cb2.add(new Cords(8, 1));
        cb2.add(new Cords(10, 1));
        b2 = new Barrier("B2", cb2);
        barriers = new Barrier[] {b1, b2};

        // ініціація світлофорів
        l1 = new Light("L1", new Cords(8,10));
        l2 = new Light("L2", new Cords(7,11));
        l3 = new Light("L3", new Cords(9,11));
        ligts = new Light[] {l1, l2, l3};

        coreInit();
        // запуск системи
//        Main(S, R, B, L, P, T);
    }

    public void coreInit() {
        Core.t1 = t1;
        Core.t2 = t2;
        Core.t3 = t3;

        Core.s1 = s1;
        Core.s2 = s2;
        Core.s3 = s3;

        Core.b1 = b1;
        Core.b2 = b2;

        Core.l1 = l1;
        Core.l2 = l2;
        Core.l3 = l3;

        Core.r12 = r12;
        Core.r13 = r13;
        Core.r1p = r1p;
        Core.r2p = r2p;
        Core.r3p = r3p;

        Core.p = p;
    }

//    @Test
    public void startRunning() {
        assertEquals(new Cords(1,14), t1.position);
        assertEquals(t1.location, r2p);
        assertEquals(t1.getLastDestination(), s2);

        System.out.println(t1.position);
        t1.move();
        System.out.println(t1.position);
    }

//    @Test
    public void changeRoad() {
        t1.position = new Cords(1,14);
        assertEquals(r2p, t1.location);

        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.move();
        t1.checkLights();
        t1.move();
        t1.move();
        t1.move();
        System.out.println(t1.location);
        assertEquals(r3p, t1.location);
    }

    @Test
    public void cycleTest() {
        Train[] ts = Core.getAllT();
        while(true) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (Train t : ts) {
                t.move();
                t.checkLights();
                t.checkBarriers();
            }

            for  (Station s : stations) { // для кожної станції
                s.releaseTrainsFromStorage();
                s.checkNewTrains();
            }

            consolePrint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void consolePrint() {
        for (Train t : Core.getAllT()) {
            System.out.println(t + ": " + t.position + "; " + t.location);
            System.out.println("Остання станція: " + t.getLastDestination() + "; наступна: " + t.getNextDestination() + "; індекс: " + t.destinationIndex);
            System.out.println();
        }
        for (Station s : stations) { // для кожної станції
            System.out.print(s + " storage: ");
            for(Train t : s.storage) {
                System.out.print(t + "; ");
            }
            System.out.println();
        }
        System.out.println();
        for (Light l : Core.getAllL()) {
            System.out.print(l + " " + (l.enable ? "+" : "-") + " ");
            // out: змінює стан світлофора l на “червоний”
            // out: світлофора l на мапі червоним кольором
        }
        System.out.println();
    }
}
