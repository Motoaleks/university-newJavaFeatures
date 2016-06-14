package Main.Models.Work;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Состояние задачи
 */
public enum Condition {
    /**
     * Выполнено
     */
    DONE(0),
    /**
     * Идёт работа
     */
    WORKING_ON(1),
    /**
     * Не готово
     */
    UNDONE(2);

    /**
     * идентификатор задачи
     */
    int id;
    Condition(int id){
        this.id = id;
    }

    /**
     * Проверка задачи на готовность
     * @return выполнена ли задача
     */
    public boolean ready(){
        return id <1;
    }
}