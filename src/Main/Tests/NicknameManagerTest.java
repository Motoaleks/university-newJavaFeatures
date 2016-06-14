package Main.Tests;

import junit.framework.TestSuite;
import Main.Models.NicknameManager;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by Aleksand Smilyanskiy on 10.06.2016.
 * "The more we do, the more we can do." ©
 */
public class NicknameManagerTest extends TestSuite {
    @Test
    public void getNickname() throws Exception {
        String result = "";
        for (int i = 0; i<10000; i++){
            result = NicknameManager.getRandomNickname();
            assertFalse("".equals(result));
        }
    }

}