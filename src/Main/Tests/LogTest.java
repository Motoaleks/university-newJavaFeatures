package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.Log;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aleksand Smilyanskiy on 11.06.2016.
 * "The more we do, the more we can do." ©
 */
public class LogTest extends TestSuite {
    @Test
    public void testLogger() throws Exception{
        final String test1 = "test";
        Log.writeString(test1);
        String result = Log.getLast();
        assertTrue(test1.equals(result));
        assertTrue(Log.getLast() == null);

        LinkedList<String> test2 = new LinkedList<String>();
        test2.add("test1");
        test2.add("test2");
        test2.add("test3");

        Log.writeString(test2.get(0));
        Log.writeString(test2.get(1));
        Log.writeString(test2.get(2));

        LinkedList<String> dump = Log.dump();
        assertTrue(dump.get(0).equals(test2.get(0)));
        assertTrue(dump.get(1).equals(test2.get(1)));
        assertTrue(dump.get(2).equals(test2.get(2)));

        LinkedList<String> empty = Log.dump();
        assertTrue(empty.isEmpty());
    }
}