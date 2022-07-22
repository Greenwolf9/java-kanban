package ru.yandex.kanban;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    private Map<Integer, Task> tasksPerId = new HashMap<>();
    private Map<Integer, Epic> epicsPerId = new HashMap<>();
    private Map<Integer, SubTask> subTasksPerId = new HashMap<>();

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @Override
    public void createNewTask(Task task) {  //создание обычной задачи
        int id = generateId();
        task.setId(id);
        tasksPerId.put(id, task);
    }
    @Override
    public void createNewEpic(Epic epic){  //создание эпика
        int id = generateId();
        epic.setId(id);
        epicsPerId.put(id, epic);
        findStatusOfEpic(epic);
        }

    @Override
    public List<SubTask> getListsOfSubTasks(Epic epic){ //создание списка подзадач
        List<SubTask> listsOfSubTasks = new ArrayList<>();
        for (int i = 0; i < epic.getSubTaskIds().size(); i++){
            int id = epic.getSubTaskIds().get(i);
            listsOfSubTasks.add(subTasksPerId.get(id));

        }
        return listsOfSubTasks;

    }
    @Override
    public Task.StatusOfTask findStatusOfEpic(Epic epic){  // вычисление статуса эпика
        List<SubTask> listsOfSubTasks = getListsOfSubTasks(epic);
        int countNew = 0;
        int countDone = 0;
        if (!listsOfSubTasks.isEmpty()) {
            for(SubTask list: listsOfSubTasks){
                if (Task.StatusOfTask.NEW.equals(list.status)){
                    countNew++;
                } else if(Task.StatusOfTask.DONE.equals(list.status)){
                    countDone++;
                }
                if (countNew == listsOfSubTasks.size()){
                    epic.status = Task.StatusOfTask.NEW;
                } else if (countDone == listsOfSubTasks.size()){
                    epic.status = Task.StatusOfTask.DONE;
                } else {
                    epic.status = Task.StatusOfTask.IN_PROGRESS;
                }
            }
        } else{
            epic.status = Task.StatusOfTask.NEW;
        } return epic.status;
    }


    @Override
    public void addNewSubTask(SubTask subTask, List<Integer> subTasksIds){ //создание списка подзадач
        int id = generateId();
        subTask.setId(id);
        subTasksIds.add(id);
        subTasksPerId.put(id, subTask);
        findStatusOfEpic(subTask.epic);
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
    public void printListOfEpicsAndSubTasks(){  // печать эпиков и сабтасков
        for(Epic epic: epicsPerId.values()){
            System.out.println(epic);
            for (int i = 0; i < getListsOfSubTasks(epic).size(); i++){
                SubTask subTask = getListsOfSubTasks(epic).get(i);
                System.out.println(subTask);
            }
        }
    }

    @Override
    public Task getTask(int id){          // получение таски
        Task task = tasksPerId.get(id);
        System.out.println(task);
        inMemoryHistoryManager.addTask(task);
        return task;
    }

    @Override
    public Epic getEpic(int id){          // получение эпика
        Epic epic = epicsPerId.get(id);
        System.out.println(epic);
        inMemoryHistoryManager.addTask(epic);
        return epic;
    }

    @Override                               // получение сабтаски
    public SubTask getSubTask(int id){
        SubTask subTask = subTasksPerId.get(id);
        System.out.println(subTask);
        inMemoryHistoryManager.addTask(subTask);
        return subTask;
    }


    @Override
    public void deleteTaskPerId(int id){ //удаление задачи по айди

        if (tasksPerId.containsKey(id)){
            tasksPerId.remove(id);
        }
        inMemoryHistoryManager.remove(id);
        }

    @Override
    public void deleteEpicPerId(int id){ //удаление задачи по айди
        if (epicsPerId.containsKey(id)){
        Epic epic = epicsPerId.get(id);
        List<Integer> ids = epic.getSubTaskIds();
        for (int i: ids){
            subTasksPerId.remove(i);
            inMemoryHistoryManager.remove(i);
        }

            epicsPerId.remove(id);
            inMemoryHistoryManager.remove(id);
        }


    }

    @Override
    public void deleteSubTaskPerId(int id){ //удаление сабтаски по айди

        if (subTasksPerId.containsKey(id)){
            subTasksPerId.remove(id);
        }
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteAllTasks(){ // удаление всех задач

        tasksPerId.clear();
        epicsPerId.clear();
        subTasksPerId.clear();
    }

    @Override
    public void updateTask(Task task, int id){ //обновление задачи по айди
        if (tasksPerId.containsKey(id)){
        tasksPerId.put(id, task);
        }else if(epicsPerId.containsKey(id)){
            epicsPerId.put(id, (Epic) task);
        }else{
            subTasksPerId.put(id, (SubTask) task);
        }
    }
}

