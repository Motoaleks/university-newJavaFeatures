package Main.UI;

import Main.Models.MessageManager;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Показать что умею default и static - представление информации о программе
 */
public interface SimpleUI{
    void stop() throws Exception;

    default void start(){
        info();
    }

    static void info(){
        name();
        version();
        author();
    }

    static void author(){
        System.out.println("Creator: Aleksandr Smilyanskiy\nGroup: 142-2");
    }
    static void name(){
        System.out.println("Programm name: " + MessageManager.getMessage("programm_name"));
    }
    static void version(){
        System.out.println("Programm version: " + MessageManager.getMessage("programm_code"));
    }
}
