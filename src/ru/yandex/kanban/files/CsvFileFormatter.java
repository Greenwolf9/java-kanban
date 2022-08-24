package ru.yandex.kanban.files;

import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.manager.HistoryManager;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static java.lang.String.valueOf;

public class CsvFileFormatter {

    public static String toString(Task task){
        StringBuilder br = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        String list =  String.join(",",
                valueOf(task.getId()),
                valueOf(task.getType()),
                task.getName(),
                valueOf(task.getStatus()),
                task.getDescription(),
                task.getName().toLowerCase(), task.getStartTime().format(formatter), valueOf(task.getDuration()));
        br.append(list);
        return br.toString();
    }

    public static String epicToString(Epic epic){
        StringBuilder br = new StringBuilder();

        String list =  String.join(",",
                valueOf(epic.getId()),
                valueOf(epic.getType()),
                epic.getName(),
                valueOf(epic.getStatus()),
                epic.getDescription(),
                epic.getName().toLowerCase(), valueOf(epic.getDuration()));
        br.append(list);
        return br.toString();
    }

    public static String subTaskToString(SubTask subTask){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return subTask.getId() + "," +
                subTask.getType() + "," +
                subTask.getName() + "," +
                subTask.getStatus() + "," +
                subTask.getDescription() + "," +
                subTask.epic.getId() + "," +
                subTask.getStartTime().format(formatter) + "," + subTask.getDuration();

    }
    public static Task fromString(String value) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        String[] list = value.split(",");
        StringBuilder input = new StringBuilder();
        if (Task.TaskType.valueOf(list[1]) == Task.TaskType.TASK) {

            input.append(list[6]).append(",").append(list[7]);
            Task task = new Task(list[2],
                    list[4], Task.StatusOfTask.valueOf(list[3]),
                    Task.TaskType.valueOf(list[1]),
                    LocalDateTime.parse(input.toString(), formatter), Integer.parseInt(list[8]));
            task.setId(Integer.parseInt(list[0]));
            return task;
        } else if (Task.TaskType.valueOf(list[1]) == Task.TaskType.EPIC){
            Epic epic = new Epic(list[2], list[4], Task.TaskType.valueOf(list[1]));
            epic.setId(Integer.parseInt(list[0]));
            epic.setStatus(Task.StatusOfTask.valueOf(list[3]));
            epic.setDuration(Long.parseLong(list[6]));
            return epic;
        } else {
            input.append(list[6] +","+list[7]);
            int id = Integer.parseInt(list[0]);
            SubTask subTask = new SubTask(new Epic(Integer.parseInt(list[5])),
                    list[2],
                    list[4],
                    Task.StatusOfTask.valueOf(list[3]),
                    Task.TaskType.valueOf(list[1]),
                    LocalDateTime.parse(input, formatter),
                    Integer.parseInt(list[8]));
            subTask.setId(id);
            subTask.setStartTime(LocalDateTime.parse(input, formatter));
            subTask.setDuration(Integer.parseInt(list[8]));

            return subTask;

        }
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
