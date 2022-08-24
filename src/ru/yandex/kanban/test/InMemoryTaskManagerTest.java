package ru.yandex.kanban.test;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.kanban.manager.InMemoryTaskManager;


class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{


    @BeforeEach
    public void setUpTaskManager(){
        taskManager = new InMemoryTaskManager();
        createTasksForTest();
    }

}