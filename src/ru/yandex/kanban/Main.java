package ru.yandex.kanban;

import ru.yandex.kanban.manager.InMemoryTaskManager;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Задача 1",
                "подробное описание", Task.TaskType.EPIC);

        SubTask subTask = new SubTask(epic,"Подзадача 2 для задачи 1",
                "Подробное описание",
                Task.StatusOfTask.NEW, Task.TaskType.SUBTASK);
        SubTask subTask1 = new SubTask(epic,"Подзадача 3 для задачи 1",
                "Подробное описание", Task.StatusOfTask.DONE, Task.TaskType.SUBTASK);
        SubTask subTask2 = new SubTask(epic,"Подзадача 4 для задачи 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK);

        Epic epic2 = new Epic("Задача 5", "Подробное описание",Task.TaskType.EPIC);
        inMemoryTaskManager.createNewEpic(epic);
        inMemoryTaskManager.addNewSubTask(subTask, epic.getSubTaskIds());
        inMemoryTaskManager.addNewSubTask(subTask1, epic.getSubTaskIds());
        inMemoryTaskManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        inMemoryTaskManager.createNewEpic(epic2);
        inMemoryTaskManager.getSubTask(4);
        inMemoryTaskManager.getSubTask(3);
        inMemoryTaskManager.getEpic(1);
        inMemoryTaskManager.getSubTask(2);
        inMemoryTaskManager.getEpic(5);
        inMemoryTaskManager.getSubTask(3);
        inMemoryTaskManager.getSubTask(2);
        inMemoryTaskManager.getInMemoryHistoryManager().getHistory();

    }
}
