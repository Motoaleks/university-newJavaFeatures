package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.TimeManager;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */
public class TimeManagerTest extends TestSuite {
    @Test
    public void getTime() throws Exception {
        String time = TimeManager.getTime();
        assertFalse(time == null);
        assertFalse("".equals(time));
    }

    @Test
    public void clock() throws Exception {
        long clock = TimeManager.clock();
        sleep(1000);
        assertTrue(TimeManager.clock() - clock > 0);
    }

}