package Main.Models.Data;

/**
 * Created by Aleksand Smilyanskiy on 11.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Информация об алкогольном напитке
 */
public class Alcohol {
    /**
     * Класс напитка (пример: вино, пиво, виски...)
     */
    public String className = "undefined";
    /**
     * Страна производитель
     */
    public String from = "undefined";
    /**
     * ID в изначальной базе данных
     */
    public String id = "undefined";
    /**
     * Масса нетто
     */
    public double litres = -1.0;
    /**
     * Полное имя напитка
     */
    public String name = "undefined";
    /**
     * Цена за тару
     */
    public double price = -1.0;

    /**
     * Напиток с незаданными параметрами
     */
    public Alcohol() {
    }

    /**
     * Информация о напитке из параметров
     * @param className {@link Alcohol#className}
     * @param from {@link Alcohol#from}
     * @param id {@link Alcohol#id}
     * @param litres {@link Alcohol#litres}
     * @param name {@link Alcohol#name}
     * @param price {@link Alcohol#price}
     */
    public Alcohol(String className, String from, String id, double litres, String name, double price) {
        this();
        this.className = className;
        this.from = from;
        this.id = id;
        this.litres = litres;
        this.name = name;
        this.price = price;
    }

    /**
     * Построение информации о напитке из строки ввода, формат .csv, делитель - запятые.
     * Пример : "WINE,CANADA,80788,4.0,HOCHTALER,32.99"
     * При неправильном кол-ве параметров объект будет с параметрами по-умолчанию.
     * @param info Строка заданного формата
     */
    public Alcohol(String info) {
        String[] data = info.split(",");
        if (data.length < 6) {
            return;
        }
        this.className = data[0] == null ? "null" : data[0];
        this.from = data[1] == null ? "null" : data[1];
        this.id = data[2] == null ? "null" : data[2];
        this.litres = Double.valueOf(data[3] == null ? "0.0" : data[3]);
        this.name = data[4] == null ? "null" : data[4];
        this.price = Double.valueOf(data[5] == null ? "0.0" : data[5]);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Alcohol)) return false;
        Alcohol otherAlco = (Alcohol) other;
        return this.className.equals(otherAlco.className) &&
                this.from.equals(otherAlco.from) &&
                this.id.equals(otherAlco.id) &&
                this.litres == otherAlco.litres &&
                this.name.equals(otherAlco.name) &&
                this.price == otherAlco.price;
    }

    @Override
    public String toString() {
        return className + "\t" + from + "\t\t" + id + "\t" + litres + "\t" + name + "\t" + price;
    }
}
