package Main.UI;

import Main.Models.Work.Task;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Добавление задачи работнику, наблюдатель
 */
public interface AddTaskInfo {
    /**
     * Добавление данной задачи
     * @param task задача
     */
    void addTask(Task task);
}
