package ru.yandex.kanban.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.kanban.manager.HttpTaskManager;
import ru.yandex.kanban.server.KVServer;
import ru.yandex.kanban.tasks.Task;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    protected KVServer kvserver;




    @BeforeEach
    public void setUpHttpTaskManager() throws IOException{
        kvserver = new KVServer();
        kvserver.start();
        taskManager = new HttpTaskManager(KVServer.PORT);
        createTasksForTest();

    }
    @AfterEach
    void stopServer(){
        kvserver.stop();
    }


    @Test
    void shouldReadFromServer() throws IOException, InterruptedException{
        taskManager.read();
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        List<Task> history = taskManager.getHistory();
        assertNotNull(history);
        assertEquals(3, history.size());
    }


}