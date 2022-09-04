package ru.yandex.kanban.manager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.kanban.client.KVTaskClient;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager{

    protected KVTaskClient client;
    private Gson gson;


    public HttpTaskManager(int port){

        client = new KVTaskClient(port);
        gson = Managers.getGson();

    }

    public KVTaskClient getClient(){
        return client;
    }
    @Override
    public void save() {
        String allTasks = gson.toJson(getTasksPerId());
        client.put("tasks/task", allTasks);
        String allEpics = gson.toJson(getEpicsPerId());
        client.put("tasks/epic", allEpics);
        String allSubTasks = gson.toJson(getSubTasksPerId());
        client.put("tasks/subtask", allSubTasks);
        String history = gson.toJson(getHistory());
        client.put("tasks/history", history);
        String prioritizedTask = gson.toJson(getPrioritizedTasks());
        client.put("tasks", prioritizedTask);
    }

    public void read() throws IOException, InterruptedException {
        String gsonTasks = getClient().load("tasks/task");
        Type typeTask = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(gsonTasks, typeTask);
        getTasksPerId().putAll(tasks);

        String gsonEpics = getClient().load("tasks/epic");
        Type typeEpic = new TypeToken<Map<Integer, Epic>>(){}.getType();
        Map<Integer, Epic> epics = gson.fromJson(gsonEpics, typeEpic);
        getEpicsPerId().putAll(epics);

        String gsonSubTasks = getClient().load("tasks/subtask");
        Type typeSubTask = new TypeToken<Map<Integer, SubTask>>(){}.getType();
        Map<Integer, SubTask> subtasks = gson.fromJson(gsonSubTasks, typeSubTask);
        getSubTasksPerId().putAll(subtasks);

        String gsonHistory = getClient().load("tasks/history");
        Type typeHistory = new TypeToken<List<Task>>(){}.getType();
        List<Task> historyOfTasks = gson.fromJson(gsonHistory, typeHistory);
        getHistory().addAll(historyOfTasks);

        String prioritizedTasks = getClient().load("tasks");
        Type typePriorityTask = new TypeToken<List<Task>>(){}.getType();
        List<Task> priorityTask = gson.fromJson(prioritizedTasks, typePriorityTask);
        getPrioritizedTasks().addAll(priorityTask);

    }



}

