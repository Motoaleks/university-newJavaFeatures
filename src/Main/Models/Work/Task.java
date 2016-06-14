package Main.Models.Work;

import Main.Models.Characters.Worker;
import Main.Models.MessageManager;
import Main.Models.TimeManager;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Абстрактное представление задачи, способной быть выполненной 4 способами
 * @param <T> Данные ожидаемые для получения
 */
public abstract class Task<T>{
    // Objects
    /**
     * Имя задачи
     */
    private String name;
    /**
     * Состояние работы
     */
    private Condition stage = Condition.UNDONE;
    /**
     * Буфер с информацией о выполнении задачи
     */
    protected StringBuffer info = new StringBuffer();
    /**
     * Исполнитель задания
     */
    protected Worker executioner;


    // Constructor
    /**
     * Создаёт задачу с именем
     *
     * @param name имя задачи
     */
    public Task(String name) {
        this.name = name;
    }


    // Getting info
    /**
     * {@link Task#name}
     */
    public String getName() {
        return name;
    }
    /**
     * {@link Task#stage}
     */
    public Condition getStage() {
        return stage;
    }
    /**
     * Выполнена ли задача
     *
     * @return Статус выполнения
     */
    public boolean ready() {
        return stage.ready();
    }
    /**
     * Получение информация о выполнении задачи
     *
     * @return Информация о выполнении задачи
     */
    public String getInfo() {
        return new String(info);
    }
    /**
     * Получение информации до работы
     * @return информация до работы
     */
    public abstract T getDataBefore();
    /**
     * Получение информации после работы
     * @return информация после работы
     */
    public abstract T getDataAfter();
    /**
     * Проверяет все ли методы вернули одинаковый результат
     * @return Одинаковый ли результат у всех методов
     */
    public abstract boolean sameOutput();
    /**
     * {@link Task#executioner}
     * @param executioner Выполнитель задания
     */
    public void setExecutioner(Worker executioner) {
        this.executioner = executioner;
    }
    /**
     * {@link Task#executioner}
     * @return Выполнитель задания
     */
    public Worker getExecutioner() {
        return executioner;
    }


    // Action
    /**
     * Запуск выполнения задачи
     */
    public void startWork() {
        long clock_total_start = TimeManager.clock();
        info.append(MessageManager.getMessage("work_start")).append(name).append("\n");
        info.append(MessageManager.getMessage("work_time")).append(TimeManager.getTime()).append("\n");
        info.append(MessageManager.getMessage("work_deli")).append("\n").append("\n");
        stage = Condition.WORKING_ON;

        // Simple
        long clock_start = TimeManager.clock();
        doSimple();
        long clock_end = TimeManager.clock();
        info.append(MessageManager.getMessage("work_simple")).append((double)(clock_end - clock_start) / 1000000000.0).append("\n");

        // Parallel
        clock_start = TimeManager.clock();
        doParallel();
        clock_end = TimeManager.clock();
        info.append(MessageManager.getMessage("work_parallel")).append((double)(clock_end - clock_start) / 1000000000.0).append("\n");

        // Java old
        clock_start = TimeManager.clock();
        doJavaOld();
        clock_end = TimeManager.clock();
        info.append(MessageManager.getMessage("work_old")).append((double)(clock_end - clock_start) / 1000000000.0).append("\n");

        // Other
        clock_start = TimeManager.clock();
        doOther();
        clock_end = TimeManager.clock();
        info.append(MessageManager.getMessage("work_other")).append((double)(clock_end - clock_start) / 1000000000.0).append("\n");

        long clock_total_end = TimeManager.clock();
        info.append(MessageManager.getMessage("work_end")).append(TimeManager.getTime()).append("\n");
        info.append(MessageManager.getMessage("work_time")).append((double)(clock_total_end - clock_total_start) / 1000000000.0).append("\n");
        info.append(MessageManager.getMessage("work_deli")).append("\n").append("\n");
        stage = Condition.DONE;
    }


    // Hidden
    /**
     * Выполнение не параллельного stream
     */
    protected abstract void doSimple();
    /**
     * Выполнение параллельного stream
     */
    protected abstract void doParallel();
    /**
     * Выполнение старыми возможностями Java
     */
    protected abstract void doJavaOld();

    /**
     * Выполнение иными способами (fork/join, etc.)
     */
    protected abstract void doOther();

    @Override
    public String toString() {
        if (stage.ready()){
            return getInfo();
        }
        else{
            return MessageManager.getMessage("work_undone") + name;
        }
    }
}
