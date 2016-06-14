package Main.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.nanoTime;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Класс представляющий единый измеритель времени
 */
public class TimeManager {
    /**
     * календарь
     */
    private static volatile Calendar calendar;
    /**
     * формат даты для вывода
     */
    private static volatile DateFormat dateFormat;

    /**
     * Получение даты и времени
     * @return дата и время
     */
    public static String getTime(){
        Calendar localCalendar = calendar;
        if (localCalendar == null){
            synchronized (TimeManager.class){
                localCalendar = calendar;
                if (localCalendar == null){
                    calendar = localCalendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                }
            }
        }
        return dateFormat.format(localCalendar.getTime());
    }

    /**
     * Измерение времени в абстрактных единицах System.nanoTime()
     * @return время в абстрактных единицах
     */
    public static long clock(){
        return nanoTime();
    }
}
