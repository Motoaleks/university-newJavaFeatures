package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Characters.Office;
import Main.Models.Characters.PoolObserver;
import Main.Models.Characters.Worker;
import Main.Models.Data.AlcoholContainerLoader;
import Main.Models.Work.Task;
import Main.Models.Work.Tasks.Sort;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */
public class OfficeTest extends TestSuite {

    private class DummyObserver implements PoolObserver {
        int i = 0;
        int j = 0;

        @Override
        public void taskDone(Task task) {
            System.out.println("> Task done: " + task.getName());
        }

        @Override
        public void poolChanged(List<Worker> newPool) {
            System.out.println("> Pool changed" + i++);
        }

        @Override
        public void allTasksDone(Worker worker) {
            System.out.println("> All tasks done by: " + worker.getNickname());
        }

        @Override
        public void taskStarted(Worker worker) {
            System.out.println("> Task started by: " + worker.getNickname());
        }
    }

    @Test
    public void testOffice(){
        DummyObserver obs = new DummyObserver();
        Office office = new Office();
        office.setPoolObserver(obs);
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        System.out.println("Workers: " + worker1.getNickname() + ", " + worker2.getNickname());
        office.addCharacter(worker1);
        office.addCharacter(worker2);
        List<Worker> workers = office.getCharacters();
        assertTrue(workers.size() == 2);
        assertTrue(workers.get(0) == worker1 || workers.get(0) == worker2);

        Task sort = new Sort(AlcoholContainerLoader.loadFromResourses(), Sort.Parameter.Price);
        office.addTask(sort, worker1.getId());

        assertTrue(obs.i == 2);

        office.fireCharacter(worker1.getId());
        assertTrue(office.getCharacters().size() == 1);

        office.addCharacter(worker1);
        office.fireAll();
        assertTrue(office.getCharacters().size() == 0);
    }
}