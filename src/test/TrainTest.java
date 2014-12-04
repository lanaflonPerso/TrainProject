package test;

import main.*;
import org.junit.Before;
import org.junit.Test;

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
        Core.init();
        sysInit();
    }

    public void sysInit() {
        t1 = Core.t1;
        t2 = Core.t2;
        t3 = Core.t3;

        s1 = Core.s1;
        s2 = Core.s2;
        s3 = Core.s3;

        b1 = Core.b1;
        b2 = Core.b2;

        l1 = Core.l1;
        l2 = Core.l2;
        l3 = Core.l3;

        r12 = Core.r12;
        r13 = Core.r13;
        r1p = Core.r1p;
        r2p = Core.r2p;
        r3p = Core.r3p;

        p = Core.p;
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
        while(true) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (Train t : Core.getAllT()) {
                t.move();
                t.checkLights();
                t.checkBarriers();
            }

            for  (Station s : Core.getAllS()) { // для кожної станції
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
        for (Station s : Core.getAllS()) { // для кожної станції
            System.out.print(s + " storage: ");
            for(Train t : s.storage) {
                System.out.print(t + "; ");
            }
            System.out.println();
        }
        System.out.println();
        for (Light l : Core.getAllL()) {
            System.out.print(l + " " + (l.enable ? "+" : "-") + "    ");
            // out: змінює стан світлофора l на “червоний”
            // out: світлофора l на мапі червоним кольором
        }
        System.out.println();
    }
}
