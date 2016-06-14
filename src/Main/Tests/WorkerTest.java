package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Characters.Worker;
import Main.Models.Data.Alcohol;
import Main.Models.Data.AlcoholContainer;
import Main.Models.Work.Task;
import Main.Models.Work.TaskObserver;
import Main.Models.Work.Tasks.Sort;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */
public class WorkerTest extends TestSuite {
    String[] alcos = new String[]{
            "WINE,CANADA,198267,3.0,COPPER MOON - MALBEC,30.99",
            "WINE,CANADA,305375,4.0,DOMAINE D'OR - DRY,32.99",
            "WINE,CANADA,53017,4.0,SOMMET ROUGE,29.99",
            "WINE,CANADA,215525,4.0,MISSION RIDGE - PREMIUM DRY WHITE,33.99",
            "WINE,USA,168971,3.0,ZINFANDEL - BIG HOUSE CARDINAL ZIN,36.99",
            "WINE,FRANCE,234559,4.0,LE VILLAGEOIS RED - CELLIERS LA SALLE,34.99",
            "WINE,CANADA,492314,16.0,SAWMILL CREEK - MERLOT,119.0",
            "WINE,CANADA,587584,4.0,SOLA,32.99",
            "WINE,CANADA,100925,0.75,GANTON & LARSEN PROSPECT - PINOT BLANC BIRCH CANOE 2011,13.99",
            "SPIRITS,IRELAND,10157,0.75,JAMESON - IRISH,34.99",
            "WINE,ITALY,102764,0.75,PINOT GRIGIO DELLE VENEZIE - RUFFINO LUMINA,15.99",
            "SPIRITS,USA,103747,0.75,MAKER'S MARK - KENTUCKY BOURBON,44.95",
            "SPIRITS,CANADA,1040,0.75,GORDONS - LONDON DRY,24.49",
            "WINE,CANADA,104679,0.75,CALONA - ARTIST SERIES RESERVE PINOT GRIS 2011/13,12.99",
            "WINE,USA,106476,0.75,PINOT NOIR - SIDURI RUSSIAN RIVER 11/12,49.99",
            "SPIRITS,BRAZIL,107029,0.7,CACHACA 61,28.95"
    };

    private class DummyObserver implements TaskObserver {
        int started = 0;
        int finished = 0;
        @Override
        public void taskDone(Task task) {
            System.out.println("+ Task done: " + finished++);
        }

        @Override
        public void allTasksDone(Worker worker) {
            System.out.println("> All tasks done by worker: " + worker.getNickname());
        }

        @Override
        public void taskStarted(Worker worker) {
            System.out.println("- Task started: " + started++);
        }
    }

    @Test
    public void testCharacter(){
        AlcoholContainer container = new AlcoholContainer();
        for (String alco: alcos){
            container.addInfo(new Alcohol(alco));
        }
        Task<AlcoholContainer> task = new Sort(container, Sort.Parameter.Price);

        DummyObserver obs = new DummyObserver();

        Worker worker = new Worker();
        worker.setTaskObserver(obs);
        worker.addTask(task);
        worker.addTask(task);
        worker.addTask(task);
        worker.startWorking();

        assertTrue(worker.isWorking());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.turnOff();
        assertTrue(!worker.isWorking());
        int i = worker.queueSize();
        assertTrue(obs.finished == obs.started || obs.finished == obs.started - 1);
    }

    @Test
    public void workerConstructors(){
        Worker worker1 = new Worker();
        assertTrue(!"".equals(worker1.getNickname()) && null != worker1.getNickname());
        assertTrue(worker1.getId() != null);

        UUID id = new UUID(10000,1000);
        Worker worker2 = new Worker(id);
        assertTrue(!"".equals(worker2.getNickname()) && null != worker2.getNickname());
        assertTrue(worker2.getId() != null);
        assertTrue(worker2.getId() == id);

        String nick = "test";
        Worker worker3 = new Worker(nick);
        assertTrue(!"".equals(worker3.getNickname()) && null != worker3.getNickname());
        assertTrue(worker3.getId() != null);
        assertTrue(worker3.getNickname().equals(nick));

        Worker worker4 = new Worker(id,nick);
        assertTrue(!"".equals(worker4.getNickname()) && null != worker4.getNickname());
        assertTrue(worker4.getId() != null);
        assertTrue(worker4.getNickname().equals(nick));
        assertTrue(worker4.getId() == id);
    }
}