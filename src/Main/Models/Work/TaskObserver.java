package Main.Models.Work;

import Main.Models.Characters.Worker;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */


public interface TaskObserver {
    void taskDone(Task task);
    void allTasksDone(Worker worker);
    void taskStarted(Worker worker);
}
