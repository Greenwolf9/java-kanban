package ru.yandex.kanban;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int id = 0;

    HashMap<Integer, Object> tasksPerId = new HashMap<>();

    public void createNewTask(Task task) {  //создание обычной задачи
        tasksPerId.put(generateId(), task);
    }

    public void createNewEpic(Epic epic) { // создание эпика
        tasksPerId.put(generateId(), epic);
    }

    public void createNewSubTask(SubTask subTask, ArrayList<SubTask> listsOfSubTasks){ //создание списка подзадач
        listsOfSubTasks.add(subTask);

    }
    public int generateId() { //генерация id
        id++;
        return id;
    }

    public void printListOfTasks() {  // вывод всех имеющихся задач
        for (Integer id : tasksPerId.keySet()) {
            Object tasks = tasksPerId.get(id);
            System.out.println(tasks);
        }
    }

    public void findTaskPerId(int id) { // поиск задачи по айди
        Object tasks = tasksPerId.get(id);
        System.out.println(tasks);
    }

    public void deleteTaskPerId(int id){ //удаление задачи по айди
        tasksPerId.remove(id);
    }

    public void deleteAllTasks(){ // удаление всех задач
        tasksPerId.clear();
    }

    public void updateTask(Object object, int id){ //обновление задачи по айди
        tasksPerId.put(id, object);
    }
}

