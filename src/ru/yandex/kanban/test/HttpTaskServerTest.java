package ru.yandex.kanban.test;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.kanban.manager.Managers;
import ru.yandex.kanban.manager.TaskManager;
import ru.yandex.kanban.server.HttpTaskServer;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private HttpTaskServer server;
    private TaskManager taskManager;
    private Gson gson;

    private Task task;
    private Epic epic;
    private SubTask subTask;
    private SubTask subTask2;

    @BeforeEach
    void shouldStartServer() throws IOException {
        taskManager = Managers.getDefault();
        gson = Managers.getGson();
        server = new HttpTaskServer(taskManager);

        epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);

        subTask = new SubTask(1,"Подзадача 3 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 12)), 40);
        subTask2 = new SubTask(1,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 20)), 40);
        task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW,
                Task.TaskType.TASK, LocalDateTime.now(), 60);

        taskManager.createNewEpic(epic);
        taskManager.createNewTask(task);
        taskManager.addNewSubTask(subTask, epic.getSubTaskIds());
        taskManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        taskManager.getTask(task.getId());
        taskManager.getEpic(epic.getId());

        server.start();
    }

    @AfterEach
    void shouldStopServer(){
        server.stop();
    }
    @Test
    void shouldGetAllTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(response.body(), type);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());

    }

    @Test
    void shouldGetAllEpics() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<Map<Integer, Epic>>(){}.getType();
        Map<Integer, Epic> epics = gson.fromJson(response.body(), type);
        assertNotNull(epics);
        assertEquals(1, epics.size());

    }

    @Test
    void shouldGetAllSubtasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<Map<Integer, SubTask>>(){}.getType();
        Map<Integer, SubTask> subtasks = gson.fromJson(response.body(), type);
        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());

    }

    @Test
    void shouldGetTaskPerId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<Task>(){}.getType();
        Task taskForTest = gson.fromJson(response.body(), type);
        assertNotNull(taskForTest);
        assertEquals(task, taskForTest);

    }

    @Test
    void shouldGetHistoryOfTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<List<Task>>(){}.getType();
        List<Task> tasks = gson.fromJson(response.body(), type);
        assertNotNull(tasks);
        assertEquals(2, tasks.size());

    }

    @Test
    void shouldGetPrioritizedTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<List<Task>>(){}.getType();
        List<Task> tasks = gson.fromJson(response.body(), type);
        assertNotNull(tasks);
        assertEquals(3, tasks.size());

    }

    @Test
    void shouldDeleteTaskPerId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());


    }

    @Test
    void shouldDeleteAllTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type type = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(response.body(), type);
        assertNull(tasks);

    }

    @Test
    void shouldCreateTask() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }

    @Test
    void shouldCreateEpic() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }

    @Test
    void shouldAddSubTask() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        String json = gson.toJson(subTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

    }


}