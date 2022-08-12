package ru.yandex.kanban.manager;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

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
    void deleteSubTaskPerId(int id);
    void deleteEpicPerId(int id);

    void deleteAllTasks();

    void updateTask(Task task, int id);

    Task getTask(int id);
    Epic getEpic(int id);

    void printListOfEpicsAndSubTasks();

    SubTask getSubTask(int id);

}

