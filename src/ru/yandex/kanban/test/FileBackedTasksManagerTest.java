package ru.yandex.kanban.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.kanban.manager.FileBackedTasksManager;
import ru.yandex.kanban.tasks.Task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{

    protected Path file;

    @BeforeEach
    public void setUpTaskManager(){
        file = Paths.get("src/tasks.csv");
        taskManager = new FileBackedTasksManager();
        createTasksForTest();
    }


    @Test
    public void shouldLoadFromFile(){
        Task task = taskManager.getTask(2);
        FileBackedTasksManager newOneFileBackedManager1 = FileBackedTasksManager.loadFromFile(new File("src/tasks.csv"));
        List<Task> listOfTasks = newOneFileBackedManager1.getHistory();
        assertFalse(listOfTasks.isEmpty());
        assertEquals(1, listOfTasks.size(), "Wrong quantity of tasks");
        Task taskForTest = listOfTasks.get(0);
        final int id = taskForTest.getId();
        assertEquals(task.getId(), id, "ID's of tasks are not equal");
    }

    @Test
    public void shouldLoadFromFileWithoutHistory(){
        FileBackedTasksManager newOneFileBackedManager1 = FileBackedTasksManager.loadFromFile(new File("src/tasks.csv"));
        assertTrue(newOneFileBackedManager1.getHistory().isEmpty());
        assertEquals(0, newOneFileBackedManager1.getHistory().size(), "history is empty");
    }


}