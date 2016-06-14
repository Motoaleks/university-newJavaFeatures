package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.MessageManager;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */
public class MessageManagerTest extends TestSuite {
    @Test
    public void readMessagesAndHasKey() throws Exception {
        MessageManager.readMessages("res/messages.txt");
        assertFalse(MessageManager.empty());
        assertTrue(MessageManager.hasMessage("programm_name"));
    }

    @Test
    public void getMessage() throws Exception {
        String name = MessageManager.getMessage("programm_name");
        assertTrue("FunnySecretary".equals(name));
    }

}