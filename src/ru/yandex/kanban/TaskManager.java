package ru.yandex.kanban;
import java.util.List;

public interface TaskManager {

    void createNewTask(Task task);

    void createNewSubTask(SubTask subTask, List<SubTask> listsOfSubTasks);

    int generateId();

    void printListOfTasks();

    void deleteTaskPerId(int id);

    void deleteAllTasks();

    void updateTask(Task task, int id);

    void getTaskOrEpic(int id);

    void getSubTask(Epic epic);

}

