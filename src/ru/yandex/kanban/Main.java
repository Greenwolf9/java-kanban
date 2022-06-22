package ru.yandex.kanban;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = Managers.getDefault(historyManager);

        Task task = new Task("Задача 1",
                "Подробное описание",
                Task.StatusOfTask.NEW);
        Task task2 = new Task("Задача 2",
                "Подробное описание",
                Task.StatusOfTask.NEW);

        Epic epic = new Epic("Задача 3",
                "подробное описание");

        SubTask subTask = new SubTask(epic,"Подзадача 1 для задачи 3",
                "Подробное описание",
                Task.StatusOfTask.NEW);
        SubTask subTask1 = new SubTask(epic,"Подзадача 2 для задачи 3",
                "Подробное описание", Task.StatusOfTask.DONE);

        Epic epic2 = new Epic("Задача 4", "Подробное описание");
        SubTask subTask1Epic2 = new SubTask(epic2, "Подзачада 1 для задачи 4",
                "Подробное описание",
                Task.StatusOfTask.DONE);
        manager.createNewTask(task);
        manager.createNewTask(task2);
        manager.createNewTask(epic);
        manager.createNewSubTask(subTask, epic.getListsOfSubTasks());
        manager.createNewSubTask(subTask1, epic.getListsOfSubTasks());
        manager.createNewTask(epic2);
        manager.createNewSubTask(subTask1Epic2, epic2.getListsOfSubTasks());
        manager.getSubTask(epic);
        manager.getTaskOrEpic(1);
        manager.getTaskOrEpic(2);
        manager.getTaskOrEpic(3);
        manager.printListOfTasks();
        historyManager.addTask(task);
        historyManager.addTask(epic);
        historyManager.addTask(epic2);
        historyManager.addTask(new Task("Задача 5", "Подробное описание", Task.StatusOfTask.NEW));
        historyManager.getHistory();
        manager.updateTask(task2, 1);
        manager.deleteTaskPerId(2);
        manager.printListOfTasks();
        manager.deleteAllTasks();
        manager.printListOfTasks();
        historyManager.getHistory();
    }
}
