package Main.Models;

import Main.resources.Log;

import java.io.*;
import java.net.URL;
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
    private static final String FILENAME = "messages.txt";
    /**
     * Хранилаще сообщений
     */
    private static volatile Map<String, String> messages = new HashMap<>();

    /**
     * Чтение файла с сообщениями
     *
     * @param filename название файла
     */
    public static void readMessages(String filename) {
        Consumer<String> mapAdd = line -> {
            String[] args = line.split(" ", 2);
            messages.put(args[0], args[1]);
        };

        InputStream in = Log.class.getResourceAsStream(filename);
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String line;
        try {
            while((line = input.readLine()) != null){
                if (!line.startsWith("#") && line.contains(" ")){
                    mapAdd.accept(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        synchronized (messages){
//            //read file into stream, try-with-resources
//            try (Stream<String> stream = Files.lines(Paths.get(filename), Charset.forName("UTF-8"))) {
//                stream.filter(line -> !line.startsWith("#") && line.contains(" "))
//                        .forEach(mapAdd);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * Есть ли в базе сообщение
     *
     * @param id идентификатор
     * @return найдено ли сообщение
     */
    public static boolean hasMessage(String id) {
        synchronized (messages) {
            return messages.containsKey(id);
        }
    }

    /**
     * Пустая ли база сообщений
     *
     * @return пустая ли база
     */
    public static boolean empty() {
        synchronized (messages) {
            return messages.isEmpty();
        }
    }

    /**
     * Получение сообщений из базы
     *
     * @param id идентификатор
     * @return сообщение
     */
    public static String getMessage(String id) {
        if (empty()){
//            URL url = Log.class.getResource("messages.txt");
//            FILENAME = url.getPath().replaceFirst("file:","").replaceFirst("/","");
//            System.out.println(FILENAME);
            readMessages(FILENAME);
//            System.out.println(System.getProperty("user.dir"));
//            System.out.println(FILENAME);
//            readMessages(FILENAME);
        }
        synchronized (messages) {
            return messages.get(id);
        }
    }
}
