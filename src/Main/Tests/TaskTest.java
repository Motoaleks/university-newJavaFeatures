package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Data.AlcoholContainer;
import Main.Models.Work.Task;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */
public class TaskTest extends TestSuite {
    private class DummyTask extends Task<AlcoholContainer> {
        AlcoholContainer alcoholContainer;
        AlcoholContainer before;
        AlcoholContainer after;

        DummyTask(AlcoholContainer alco){
            super("DummyTask test");
            this.alcoholContainer = alco;
        }

        @Override
        public AlcoholContainer getDataBefore() {
            return before;
        }

        @Override
        public AlcoholContainer getDataAfter() {
            return after;
        }

        @Override
        public boolean sameOutput() {
            return true;
        }

        @Override
        protected void doSimple() {
            before = new AlcoholContainer();
            System.out.println("Doing simple...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Simple done.");
            System.out.println();
        }

        @Override
        protected void doParallel() {
            System.out.println("Doing parallel...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Parallel done.");
            System.out.println();
        }

        @Override
        protected void doJavaOld() {
            System.out.println("Doing JavaOld...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("JavaOld done.");
            System.out.println();
        }

        @Override
        protected void doOther() {
            System.out.println("Doing other...");
            try {
                Thread.sleep(300);
                after = new AlcoholContainer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Other done.");
            System.out.println();
        }
    }

    @Test
    public void testCreationRunOutputInfo() throws Exception{
        AlcoholContainer alco = new AlcoholContainer();
        Task<AlcoholContainer> alcoholContainerTask = new DummyTask(alco);
        assertFalse(alcoholContainerTask.ready());
        System.out.println(alcoholContainerTask.getStage());

        alcoholContainerTask.startWork();
        assertTrue(alcoholContainerTask.getDataAfter() != null);
        assertTrue(alcoholContainerTask.getDataBefore() != null);

        assertTrue(alcoholContainerTask.ready());
        System.out.println(alcoholContainerTask.getInfo());
        assertTrue(alcoholContainerTask.getInfo().equals(alcoholContainerTask.toString()));
    }
}