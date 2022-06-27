package ru.yandex.kanban;
import java.util.List;

public interface TaskManager {

    void createNewTask(Task task);
    void createNewEpic(Epic epic);

    void addNewSubTask(SubTask subTask, List<Integer> subTasksIds);

    List<SubTask> getListsOfSubTasks(Epic epic);
    Task.StatusOfTask findStatusOfEpic(Epic epic);

    int generateId();

    void printListOfTasks();

    void deleteTaskPerId(int id);

    void deleteAllTasks();

    void updateTask(Task task, int id);

    Task getTask(int id);
    Epic getEpic(int id);

    void printListOfEpicsAndSubTasks();

    SubTask getSubTask(Epic epic);

}

