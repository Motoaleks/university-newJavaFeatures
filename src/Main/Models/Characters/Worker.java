package Main.Models.Characters;

import Main.Models.NicknameManager;
import Main.Models.Work.Task;
import Main.Models.Work.TaskObserver;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by Aleksand Smilyanskiy on 09.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Работник, способный последовательно выполнять очередь заданий
 */
public class Worker {
    // Objects
    /**
     * ID
     */
    private UUID id;
    /**
     * Никнейм
     */
    private String nickname;
    /**
     * Очередь выполнения задач
     */
    private final LinkedList<Task> taskQueue = new LinkedList<>();
    /**
     * Состояние цикла по выполнению задач.
     */
    private boolean working = false;
    /**
     * Наблюдатель за состоянием выполнения задач
     */
    private TaskObserver taskObserver;

    // Constructors

    /**
     * Создание пользователя по id и никнейму
     * @param id {@link Worker#id}
     * @param nickname {@link Worker#nickname}
     */
    public Worker(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    /**
     * Создание работника с заданным никнеймом, id будет присвоен случайно.
     * @param nickname {@link Worker#nickname}
     */
    public Worker(String nickname) {
        this(UUID.randomUUID(), nickname);
    }
    /**
     * Создание пользователя с заданным id, никнейм будет задан случайно.
     * @param id {@link Worker#id}
     */
    public Worker(UUID id) {
        this(id, NicknameManager.getRandomNickname());
    }
    /**
     * Создание пользователя с случайным id и никнеймом.
     */
    public Worker() {
        this(UUID.randomUUID(), NicknameManager.getRandomNickname());
    }

    // Properties
    /**
     * Начать работу над выполнением задач.
     */
    public void startWorking() {
        working = true;
        new Thread(() -> {
            while (working) {

                Task newTask;
                try {
                    synchronized (taskQueue) {
                        newTask = taskQueue.getFirst();
                    }
                } catch (NoSuchElementException ignored) {
                    try {
                        if (taskObserver != null) {
                            taskObserver.allTasksDone(this);
                        }
                        synchronized (taskQueue) {
                            taskQueue.wait();
                        }
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                }

                if (taskObserver != null) {
                    taskObserver.taskStarted(this);
                }
                newTask.startWork();
                synchronized (taskQueue) {
                    taskQueue.poll();
                }
                if (taskObserver != null) {
                    taskObserver.taskDone(newTask);
                }


            }
        }).start();
    }
    /**
     * {@link Worker#isWorking()}
     * @return {@link Worker#isWorking()}
     */
    public boolean isWorking() {
        return working;
    }
    /**
     * Деактивация цикла по выполнению задач.
     */
    public void turnOff() {
        working = false;
        synchronized (taskQueue) {
            taskQueue.notify();
        }
    }
    /**
     * Добавить задачу в очердеь выполнения
     * @param task Задача, необходимая для выполнения
     */
    public void addTask(Task task) {
        synchronized (taskQueue) {
            task.setExecutioner(this);
            taskQueue.add(task);
            taskQueue.notify();
        }
    }
    /**
     * Получение размера очереди задача.
     * @return Размер очереди задач
     */
    public int queueSize() {
        synchronized (taskQueue) {
            return taskQueue.size();
        }
    }
    /**
     * Установка наблюдателя за выполнением задач.
     * @param taskObserver {@link Worker#taskObserver}
     */
    public void setTaskObserver(TaskObserver taskObserver) {
        this.taskObserver = taskObserver;
    }

    // Getters

    /**
     * {@link Worker#nickname}
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * {@link Worker#id}
     */
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "name<" + nickname + ">\tid: " + id;
    }
}
