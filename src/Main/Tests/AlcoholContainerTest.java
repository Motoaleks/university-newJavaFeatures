package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Data.Alcohol;
import Main.Models.Data.AlcoholContainer;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Vector;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 11.06.2016.
 * "The more we do, the more we can do." ©
 */
public class AlcoholContainerTest extends TestSuite {
    @Parameterized.Parameter
    private final String file = "data.csv";

    @Test
    public void AGDtest() throws Exception {
        // AGD - add, get, delete
        AlcoholContainer container = new AlcoholContainer();
        Alcohol alco = new Alcohol("WINE,CANADA,80788,4.0,HOCHTALER,32.99");
        container.addInfo(alco);
        assertTrue(container.getInfo(0).equals(alco));

        boolean testingDelete = false;
        try{
            container.deleteInfo(0);
            container.getInfo(0);
        } catch (ArrayIndexOutOfBoundsException e){
            testingDelete = true;
        }
        assertTrue(testingDelete);
    }

    @Test
    public void createFromFileAndGetContainer() throws Exception{
        AlcoholContainer container = new AlcoholContainer(file);
        Vector<Alcohol> alcohols = container.getStorage();
        assertTrue(alcohols != null);
    }


}