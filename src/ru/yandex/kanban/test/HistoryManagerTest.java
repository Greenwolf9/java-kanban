package ru.yandex.kanban.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.kanban.manager.HistoryManager;
import ru.yandex.kanban.manager.InMemoryHistoryManager;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    HistoryManager historyManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private SubTask subTask2;

    @BeforeEach
    public void createTasksForTest(){
        historyManager = new InMemoryHistoryManager();
        epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);
        epic.setId(1);
        task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW, Task.TaskType.TASK);
        task.setId(2);
        subTask = new SubTask(epic,"Подзадача 3 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.NEW, Task.TaskType.SUBTASK);
        subTask.setId(3);
        subTask2 = new SubTask(epic,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK);
        subTask2.setId(4);

    }

    @Test
    void addTask() {
        historyManager.addTask(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void removeFirstTask() {
        historyManager.addTask(epic);
        historyManager.addTask(task);
        historyManager.addTask(subTask);
        historyManager.addTask(subTask2);
        List<Task> history = historyManager.getHistory();
        assertEquals(4, history.size());
        historyManager.remove(epic.getId());
        history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task, history.get(2));
        assertEquals(subTask, history.get(1));
        assertEquals(subTask2, history.get(0));
    }

    @Test
    void removeLastTask() {
        historyManager.addTask(epic);
        historyManager.addTask(task);
        historyManager.addTask(subTask);
        historyManager.addTask(subTask2);
        List<Task> history = historyManager.getHistory();
        assertEquals(4, history.size());
        historyManager.remove(subTask2.getId());
        history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(epic, history.get(2));
        assertEquals(task, history.get(1));
        assertEquals(subTask, history.get(0));
    }

    @Test
    void removeTaskInTheMiddle() {
        historyManager.addTask(epic);
        historyManager.addTask(task);
        historyManager.addTask(subTask);
        historyManager.addTask(subTask2);
        List<Task> history = historyManager.getHistory();
        assertEquals(4, history.size());
        historyManager.remove(task.getId());
        history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(epic, history.get(2));
        assertEquals(subTask, history.get(1));
        assertEquals(subTask2, history.get(0));
    }

    @Test
    public void addTwiceTheSameTask(){
        historyManager.addTask(task);
        historyManager.addTask(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    void getHistory() {
        historyManager.addTask(epic);
        historyManager.addTask(task);
        historyManager.addTask(subTask);
        historyManager.addTask(subTask2);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history);
        assertEquals(4, history.size());
    }
}