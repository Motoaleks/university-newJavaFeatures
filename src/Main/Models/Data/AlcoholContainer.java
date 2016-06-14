package Main.Models.Data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Aleksand Smilyanskiy on 11.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Контейнер для алкогольных напитков
 */
public class AlcoholContainer implements CompleteData {
    /**
     * Контейнер
     */
    private Vector<Alcohol> storage = new Vector<>();

    /**
     * Создание алкогольного контейнера
     */
    public AlcoholContainer() {
    }

    /**
     * Создание контейнера и чтение данных из файла
     *
     * @param filename Путь к файлу csv заданного формата
     */
    public AlcoholContainer(String filename) {
        this();
        Consumer<String> addAlchohol = line -> {
            storage.add(new Alcohol(line));
        };

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filename), Charset.forName("UTF-8"))) {
            stream.filter(line -> !line.startsWith("#"))
                    .forEach(addAlchohol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создание контейнера из вектора алкогольных напитков
     *
     * @param alcohols Вектор напитков
     */
    public AlcoholContainer(Vector<Alcohol> alcohols) {
        this();
        storage = alcohols;
    }

    /**
     * Добавление информации об алкоголе
     *
     * @param info {@link Alcohol}
     */
    public void addInfo(Alcohol info) {
        storage.add(info);
    }

    /**
     * Добавление к контейнеру другого контейнера
     *
     * @param container Добавляемый контейнер
     */
    public void concatContainer(AlcoholContainer container) {
        storage.addAll(container.getStorage());
    }

    /**
     * Получение информации об алкоголе по индексу в контейнере
     *
     * @param index индекс
     * @return {@link Alcohol}
     * @throws ArrayIndexOutOfBoundsException когда нету такого
     */
    public Alcohol getInfo(int index) throws ArrayIndexOutOfBoundsException {
        return storage.get(index);
    }

    /**
     * Удаление информации об алкоголе по индексу
     *
     * @param index индекс
     */
    public void deleteInfo(int index) {
        storage.removeElementAt(index);
    }

    /**
     * Получение хранилища
     *
     * @return хранилище алкоголя
     */
    public Vector<Alcohol> getStorage() {
        return storage;
    }

    /**
     * Получение размера хранилища
     *
     * @return размер хранилища
     */
    public int size() {
        return storage.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof AlcoholContainer)) return false;
        AlcoholContainer otherAlco = (AlcoholContainer) other;

        Vector<Alcohol> otherAlcohol = otherAlco.getStorage();
        Vector<Alcohol> thisAlcohol = this.getStorage();

        if (otherAlco.size() != thisAlcohol.size()) return false;
        for (Alcohol alcohol : otherAlcohol) {
            if (!thisAlcohol.contains(alcohol)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String dataIn() {
        StringBuilder dataInContainer = new StringBuilder();
        storage.stream().forEach((x) -> {
            dataInContainer.append(x).append("\n");
        });
        return new String(dataInContainer);
    }

    @Override
    public String toString() {
        return dataIn();
    }
}
