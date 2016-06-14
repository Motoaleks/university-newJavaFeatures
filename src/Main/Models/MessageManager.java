package Main.Models;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Представляет собой хранилище сообщений для вывода пользователю из приложения
 */
public class MessageManager {
    /**
     * Имя файла с сообщениями
     */
    private static final String FILENAME = MessageManager.class.getResource("../resources/messages.txt").getPath().replaceFirst("/","");
    /**
     * Хранилаще сообщений
     */
    private static volatile Map<String, String> messages = new HashMap<>();

    static {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(FILENAME);
        readMessages(FILENAME);
    }

    /**
     * Чтение файла с сообщениями
     * @param filename название файла
     */
    public static void readMessages(String filename){
        Consumer<String> mapAdd = line ->{
            String[] args = line.split(" ",2);
            messages.put(args[0],args[1]);
        };

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filename), Charset.forName("UTF-8"))) {
            stream.filter(line -> !line.startsWith("#") && line.contains(" "))
                    .forEach(mapAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Есть ли в базе сообщение
     * @param id идентификатор
     * @return найдено ли сообщение
     */
    public static boolean hasMessage(String id){
        synchronized (messages){
            return messages.containsKey(id);
        }
    }

    /**
     * Пустая ли база сообщений
     * @return пустая ли база
     */
    public static boolean empty(){
        synchronized (messages){
            return  messages.isEmpty();
        }
    }

    /**
     * Получение сообщений из базы
     * @param id идентификатор
     * @return сообщение
     */
    public static String getMessage(String id){
        synchronized (messages){
            return messages.get(id);
        }
    }
}
