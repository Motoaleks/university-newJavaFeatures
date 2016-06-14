package Main.Models.Characters;

import Main.Models.Work.Task;
import Main.Models.Work.TaskObserver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Офис с работниками (контейнер работников).
 */
public class Office implements TaskObserver{
    // Objects
    /**
     * Хранилище работников
     */
    private HashMap<UUID, Worker> staff = new HashMap<>();
    /**
     * Наблюдатель за изменением офиса
     */
    private PoolObserver poolObserver;

    /**
     * Получение списка работников
     * @return Список работников
     */
    public List<Worker> getCharacters(){
        return new LinkedList<Worker>(staff.values());
//        Stream<Worker> stream = staff.values().stream();
//        return stream.map((character -> character.getId())).collect(Collectors.toList());
    }

    /**
     * Добавление работника
     * @param worker Работник
     */
    public void addCharacter(Worker worker){
        worker.setTaskObserver(this);
        worker.startWorking();
        staff.put(worker.getId(), worker);
        poolChanged();
    }

    /**
     * Событие - произошло изменение списка сотрудников
     */
    private void poolChanged(){
        if (poolObserver != null)
            poolObserver.poolChanged(getCharacters());
    }

    /**
     * Увольнение сотрудника с данным id
     * @param id {@link Worker#id}
     */
    public void fireCharacter(UUID id){
        Worker person = staff.get(id);
        if (person == null){
            return;
        }
        person.turnOff();
        staff.remove(id);
        poolChanged();
    }

    /**
     * Увольнение сотрудника
     * @param worker {@link Worker}
     */
    public void fireCharacter(Worker worker){
        Worker person = staff.get(worker.getId());
        if (person == null){
            return;
        }
        person.turnOff();
        staff.remove(worker);
    }

    /**
     * Установка наблюдателя за офисом
     * @param poolObserver наблюдатель за офисом
     */
    public void setPoolObserver(PoolObserver poolObserver){
        this.poolObserver = poolObserver;
    }

    /**
     * Добавление задания конкретному работнику
     * @param task {@link Task}
     * @param id {@link Worker}
     */
    public void addTask(Task task, UUID id){
        Worker person = staff.get(id);
        if (person == null)
            return;
        person.addTask(task);
    }

    /**
     * Увольнение всех работников c завершением работы каждого.
     */
    public void fireAll(){
        for(Worker person:staff.values()){
            person.turnOff();
        }
        staff.clear();
    }



    @Override
    public void taskDone(Task task) {
        if (poolObserver != null){
            poolObserver.taskDone(task);
        }
    }

    @Override
    public void allTasksDone(Worker worker) {
        if (poolObserver == null){
            return;
        }
        poolObserver.allTasksDone(worker);
    }

    @Override
    public void taskStarted(Worker worker) {
        if (poolObserver == null){
            return;
        }
        poolObserver.taskStarted(worker);
    }
}
