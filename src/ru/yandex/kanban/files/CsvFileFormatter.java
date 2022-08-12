package ru.yandex.kanban.files;

import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.manager.HistoryManager;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static java.lang.String.valueOf;

public class CsvFileFormatter {

    public static String toString(Task task){
        StringBuilder br = new StringBuilder();

        String list =  String.join(",",
                valueOf(task.getId()),
                valueOf(task.getType()),
                task.getName(),
                valueOf(task.getStatus()),
                task.getDescription(),
                task.getName().toLowerCase());
        br.append(list);
        return br.toString();
    }

    public static String subTaskToString(SubTask subTask){

        return subTask.getId() + "," +
                subTask.getType() + "," +
                subTask.getName() + "," +
                subTask.getStatus() + "," +
                subTask.getDescription() + "," + subTask.epic.getId();

    }

    public static Task fromString(String value){
        String[] list = value.split(",");
        Task task = new Task(list[2],
                list[4], Task.StatusOfTask.valueOf(list[3]),
                Task.TaskType.valueOf(list[1]));
        task.setId(Integer.parseInt(list[0]));
        return task;
    }
    public static Epic epicFromString(String value){
        String[] list = value.split(",");
        Epic epic = new Epic(list[2], list[4], Task.TaskType.valueOf(list[1]));
        epic.setId(Integer.parseInt(list[0]));
        epic.status = Task.StatusOfTask.valueOf(list[3]);
        return epic;
    }

    public static SubTask subTaskFromString(String value){
        String[] list = value.split(",");
        int id = Integer.parseInt(list[0]);
        SubTask subTask = new SubTask(new Epic(Integer.parseInt(list[5])),
                list[2], list[4],
                Task.StatusOfTask.valueOf(list[3]),
                Task.TaskType.valueOf(list[1]));
        subTask.setId(id);
        return subTask;
    }

    public static String historyToString(HistoryManager manager){
        List<Task> historyOfTasks = manager.getHistory();
        List<String> ids = new ArrayList<>();
        if (!historyOfTasks.isEmpty()){
            for (Task task : historyOfTasks) {
                ids.add(String.valueOf(task.getId()));
            }
        }
        return String.join(",", ids);
    }

    public static List<Integer> historyFromString(String value){
        List<Integer> listOfId = new LinkedList<>();
        String[] list = value.split(",");
        for(int i = 0; i<list.length; i++){
        listOfId.add(Integer.parseInt(list[i]));
        }
        return listOfId;
    }
}
