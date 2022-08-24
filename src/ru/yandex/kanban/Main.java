package ru.yandex.kanban;

import ru.yandex.kanban.manager.InMemoryTaskManager;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);

        SubTask subTask = new SubTask(epic,"Подзадача 3 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 12)), 40);
        SubTask subTask2 = new SubTask(epic,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 20)), 40);
        Task task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW,
                Task.TaskType.TASK, LocalDateTime.now(), 60);

        SubTask subTask3 = new SubTask(epic,"Подзадача 5 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 32)), 40);

        inMemoryTaskManager.createNewEpic(epic);
        inMemoryTaskManager.createNewTask(task);

        inMemoryTaskManager.addNewSubTask(subTask, epic.getSubTaskIds());
        inMemoryTaskManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        inMemoryTaskManager.addNewSubTask(subTask3, epic.getSubTaskIds());
        inMemoryTaskManager.getSubTask(4);
        inMemoryTaskManager.getSubTask(3);
        inMemoryTaskManager.getEpic(1);
        inMemoryTaskManager.getTask(2);
        inMemoryTaskManager.getInMemoryHistoryManager().getHistory();
        System.out.println(inMemoryTaskManager.getPrioritizedTasks());

    }
}
