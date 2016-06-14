package Main.Models;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Интерфейс простого лога, обязательный вывод сообщения
 */
@FunctionalInterface
public interface SimpleLog {
    /**
     * Вывод сообщения
     * @param text сообщение
     */
    abstract void appendText(String text);
}
