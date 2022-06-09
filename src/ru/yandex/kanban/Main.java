package ru.yandex.kanban;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task = new Task("Изучить наследование в Java",
                "тут будет подробное описание задачи",
                "NEW");
        Task task2 = new Task("Изучить инкапсуляцию в Java",
                "тут будет подробное описание задачи",
                task.getStatus());

        Epic epic = new Epic("Сдать финальный проект 3 спринта",
                "тут будет подробное описание");

        SubTask subTask = new SubTask(epic,"Внимательно прочитать ТЗ",
                "тут будет подробное описание",
                "NEW");
        SubTask subTask1 = new SubTask(epic,"Составить блок-схему",
                "тут будет подробное описание", "DONE");

        Epic epic2 = new Epic("Покорить мир", "тут будет подробное описание");
        SubTask subTask1Epic2 = new SubTask(epic2, "Выпить кофе и достать карту мира",
                "тут будет подробное описание",
                "DONE");
        manager.createNewTask(task);
        manager.createNewTask(task2);
        manager.createNewEpic(epic);
        manager.createNewSubTask(subTask, epic.listsOfSubTasks);
        manager.createNewSubTask(subTask1, epic.listsOfSubTasks);
        manager.createNewEpic(epic2);
        manager.createNewSubTask(subTask1Epic2, epic2.listsOfSubTasks);
        manager.printListOfTasks();
        manager.updateTask(task2, 1);
        manager.deleteTaskPerId(2);
        manager.printListOfTasks();
        manager.deleteAllTasks();
        manager.printListOfTasks();

    }
}
