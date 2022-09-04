package ru.yandex.kanban;

import ru.yandex.kanban.manager.HttpTaskManager;
import ru.yandex.kanban.manager.Managers;
import ru.yandex.kanban.manager.TaskManager;
import ru.yandex.kanban.server.KVServer;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        new KVServer().start();
        TaskManager httpTaskManager = Managers.getDefault();



        Epic epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);

        SubTask subTask = new SubTask(1,"Подзадача 3 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 12)), 40);
        SubTask subTask2 = new SubTask(1,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 20)), 40);
        Task task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW,
                Task.TaskType.TASK, LocalDateTime.now(), 60);

        SubTask subTask3 = new SubTask(1,"Подзадача 5 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 32)), 40);


        httpTaskManager.createNewEpic(epic);
        httpTaskManager.getEpic(1);
        httpTaskManager.createNewTask(task);
        httpTaskManager.getTask(2);
        httpTaskManager.addNewSubTask(subTask, epic.getSubTaskIds());
        httpTaskManager.getSubTask(3);
        httpTaskManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        httpTaskManager.getSubTask(4);
        httpTaskManager.getHistory();

        HttpTaskManager newOne = new HttpTaskManager(KVServer.PORT);
        newOne.read();
        newOne.getPrioritizedTasks();





    }
}
