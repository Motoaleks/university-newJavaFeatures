package Main.Models;

import Main.resources.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by Aleksand Smilyanskiy on 10.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Генератор никнеймов
 */
public class NicknameManager {
    // objects
    /**
     * Генератор рандома
     */
    private static volatile Random generator = new Random();
    /**
     * Путь к файлу префиксов
     */
    private static final String prefixesFile = "prefixes.txt";
    /**
     * Путь к файлу корней
     */
    private static final String rootsFile = "roots.txt";

    // privates

    /**
     * Считает кол-во строк в файле
     * @param filename имя файла
     * @return кол-во строчек
     * @throws IOException ошибка при открытии
     */
    private static int countLines(String filename) throws IOException {
        InputStream is = Log.class.getResourceAsStream(filename);
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    /**
     * Чтение файла и получение случайной строки из него
     *
     * @param filename путь до файла
     * @return случайная строка
     */
    private static String readFileGetRandom(String filename) {
        // file length
        Path path = Paths.get(filename);
        int lineCount = 0;
        try {
            lineCount = countLines(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // generate random int
        Random rnd = generator;
        int lineNum = rnd.nextInt(lineCount - 1);
        lineNum += 1;

        InputStream in = Log.class.getResourceAsStream(filename);
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        String line;
        int i = 0;
        try {
            while ((line = input.readLine()) != null) {
                if (i++ != lineNum)
                    continue;
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
//        Stream<String> lines = null;
//        try {
//            lines = Files.lines(Paths.get(filename));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lines
//                .skip(line - 1)
//                .findFirst().orElse("null");
    }

    /**
     * Получение префикса слова (обязательно)
     *
     * @return префикс слова
     */
    private static String getPrefix() {
        return readFileGetRandom(prefixesFile);
    }

    /**
     * Получение корня слова
     *
     * @return Корень слова или пустая строка
     */
    private static String getBase() {
        Random rnd = generator;
        if (rnd.nextBoolean()) {
            return readFileGetRandom(rootsFile);
        }
        return "";
    }

    /**
     * Получение окончания слова - цифры
     *
     * @return окончание слова или пустая строка
     */
    private static String getEnd() {
        // generate random int
        Random rnd = generator;
        if (rnd.nextInt(100) > 80) {
            return String.valueOf(rnd.nextInt(100));
        }
        return "";
    }

    // To user

    /**
     * Получение никнейма
     *
     * @return новый никнейм
     */
    public static String getRandomNickname() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPrefix());
        builder.append(getBase());
        builder.append(getEnd());
        return builder.toString();
    }
}
