package Main.resources;

import Main.Models.SimpleLog;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Aleksand Smilyanskiy on 11.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Лог. Позволяет осуществлять логирование из любого места программы
 */
public class Log {
    /**
     * Лимит на хранение сообщений
     */
    private static final int MAX_MESSAGES = 500;
    /**
     * Архив сообщений
     */
    private static LinkedList<String> messageQueue;
    /**
     * Реализует вывод сообщений с помощью наблюдателя
     */
    private static SimpleLog simpleLog;

    /**
     * Возвращение архива сообщений
     * @return архив сообщений
     */
    private static LinkedList<String> getMessageQueue(){
        LinkedList<String> out = messageQueue;
        if (out == null){
            synchronized (Log.class){
                out = messageQueue;
                if (out == null){
                    messageQueue = out = new LinkedList<String>();
                }
            }
        }
        return out;
    }

    /**
     * Написание строки в лог
     * @param message сообщение
     */
    public static void writeString(String message){
        LinkedList<String> out = getMessageQueue();
        synchronized (messageQueue){
            if (out.size() == MAX_MESSAGES){
                out.poll();
            }
            out.addLast(message);
            if (simpleLog != null){
                simpleLog.appendText(message);
            }
        }
    }

    /**
     * Последнее сообщение
     * @return последнее сообщение
     */
    public static String getLast(){
        synchronized (messageQueue){
            Deque<String> out = messageQueue;
            if (out == null){
                return null;
            }
            return out.poll();
        }
    }

    /**
     * Выкачивание архива сообщений
     * @return
     */
    public static LinkedList<String> dump(){
        LinkedList<String> res = (LinkedList<String>) getMessageQueue().clone();
        messageQueue.clear();
        return res;
    }

    /**
     * Установка наблюдателя для вывода сообщений
     * @param log наблюдатель
     */
    public static void setSimpleLog(SimpleLog log){
        simpleLog = log;
    }
}
