package ru.yandex.kanban.manager;

import ru.yandex.kanban.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();
}
