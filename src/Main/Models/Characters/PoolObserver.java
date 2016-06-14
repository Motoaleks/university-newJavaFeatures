package Main.Models.Characters;

import Main.Models.Work.Task;

import java.util.List;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Наблюдатель за офисом.
 */
public interface PoolObserver {
    /**
     * Произошло выполнение задачи.
     * @param task Выполненная задача
     */
    public void taskDone(Task task);
    /**
     * Изменился список сотрудников.
     * @param newPool новый список сотрудников
     */
    public void poolChanged(List<Worker> newPool);
    /**
     * Сотрудник выполнил все поставленные ему задачи.
     * @param worker Сотрудник
     */
    public void allTasksDone(Worker worker);
    /**
     * Сотрудник начал работу за заданием.
     * @param worker Сотрудник
     */
    public void taskStarted(Worker worker);
}
