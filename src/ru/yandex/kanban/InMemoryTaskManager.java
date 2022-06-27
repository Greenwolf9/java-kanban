package ru.yandex.kanban;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    int id = 0;

    protected Map<Integer, Task> tasksPerId = new HashMap<>();
    protected Map<Integer, Epic> epicsPerId = new HashMap<>();
    protected Map<Integer, SubTask> subTasksPerId = new HashMap();

    @Override
    public void createNewTask(Task task) {  //создание обычной задачи
        tasksPerId.put(generateId(), task);
    }
    @Override
    public void createNewEpic(Epic epic){
        epicsPerId.put(generateId(), epic);
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
    public Task.StatusOfTask findStatusOfEpic(Epic epic){
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
    public void printListOfEpicsAndSubTasks(){
        for(Epic epic: epicsPerId.values()){
            System.out.println(epic);
            for (int i = 0; i < getListsOfSubTasks(epic).size(); i++){
                SubTask subTask = getListsOfSubTasks(epic).get(i);
                System.out.println(subTask);
            }
        }
    }

    @Override
    public Task getTask(int id){          // получение таски или эпика
        Task task = tasksPerId.get(id);
        System.out.println(task);
        return task;
    }

    @Override
    public Epic getEpic(int id){          // получение таски или эпика
        Epic epic = epicsPerId.get(id);
        System.out.println(epic);
        return epic;
    }

    @Override                               // получение сабтаски
    public SubTask getSubTask(Epic epic){
        SubTask subTask = null;
        for (int i = 0; i < getListsOfSubTasks(epic).size(); i++){
            subTask = getListsOfSubTasks(epic).get(i);
            System.out.println(subTask);

        } return subTask;
    }


    @Override
    public void deleteTaskPerId(int id){ //удаление задачи по айди

        if (tasksPerId.containsKey(id)){
            tasksPerId.remove(id);
        } else if (epicsPerId.containsKey(id)){
            epicsPerId.remove(id);
        }else{
            subTasksPerId.remove(id);
        }
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

