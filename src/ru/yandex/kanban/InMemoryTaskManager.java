package ru.yandex.kanban;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    int id = 0;

    protected Map<Integer, Task> tasksPerId = new HashMap<>();

    @Override
    public void createNewTask(Task task) {  //создание обычной задачи
        tasksPerId.put(generateId(), task);
    }

    @Override
    public void createNewSubTask(SubTask subTask, List<SubTask> listsOfSubTasks){ //создание списка подзадач
        listsOfSubTasks.add(subTask);
    }

    @Override
    public int generateId() { //генерация id
        id++;
        return id;
    }

    @Override
    public void printListOfTasks() {  // вывод всех имеющихся задач
        for (Task task : tasksPerId.values()) {
            System.out.println(task);
        }
    }

    @Override
    public void getTaskOrEpic(int id){          // получение таски или эпика
        Task task = tasksPerId.get(id);
        System.out.println(task);
    }

    @Override                               // получение сабтаски
    public void getSubTask(Epic epic){

        for (int i = 0; i < epic.getListsOfSubTasks().size(); i++){
            SubTask subTask = epic.getListsOfSubTasks().get(i);
            System.out.println(subTask);
        }
    }

    @Override
    public void deleteTaskPerId(int id){ //удаление задачи по айди
        tasksPerId.remove(id);
    }

    @Override
    public void deleteAllTasks(){ // удаление всех задач
        tasksPerId.clear();
    }

    @Override
    public void updateTask(Task task, int id){ //обновление задачи по айди
        tasksPerId.put(id, task);
    }
}

