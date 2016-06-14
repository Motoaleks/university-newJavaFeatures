package Main.Models;

import java.io.IOException;
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
    private static final String prefixesFile = NicknameManager.class.getResource("../resources/prefixes.txt").getPath().replaceFirst("/","");
    /**
     * Путь к файлу корней
     */
    private static final String rootsFile = NicknameManager.class.getResource("../resources/roots.txt").getPath().replaceFirst("/","");

    // privates

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
            lineCount = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }

        // generate random int
        Random rnd = generator;
        int line = rnd.nextInt(lineCount - 1);
        line += 1;

        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines
                .skip(line - 1)
                .findFirst().orElse("null");
    }
    /**
     * Получение префикса слова (обязательно)
     * @return префикс слова
     */
    private static String getPrefix() {
        return readFileGetRandom(prefixesFile);
    }
    /**
     * Получение корня слова
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
