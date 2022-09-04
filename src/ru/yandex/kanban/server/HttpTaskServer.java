package ru.yandex.kanban.server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.kanban.manager.FileBackedTasksManager;
import ru.yandex.kanban.manager.Managers;
import ru.yandex.kanban.manager.TaskManager;
import com.google.gson.Gson;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.regex.Pattern;




public class HttpTaskServer {
    public static final int PORT = 8080;
    private final static Charset CHARSET_BY_DEFAULT = Charset.defaultCharset();
    private TaskManager taskManager;

    private final HttpServer server;
    private Gson gson;


    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        gson = Managers.getGson();

    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(CHARSET_BY_DEFAULT);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }



    public void start(){
        server.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop(){
        server.stop(0);
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");
    }


    class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) {
            System.out.println("Началась обработка /tasks запроса от клиента.");
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            try {

                switch (method) {
                    case "GET":
                        if(Pattern.matches("^/tasks$", path)){
                            final String allTasksToJson = gson.toJson(taskManager.getPrioritizedTasks());
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else{
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/task$", path)){
                            final String allTasksToJson = gson.toJson(taskManager.getTasksPerId());
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/history$", path)){
                            final String allTasksToJson = gson.toJson(taskManager.getHistory());
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/task/\\d$", path)){

                            final int id = Integer.parseInt(path.replaceFirst("/tasks/task/",""));
                            final String allTasksToJson = gson.toJson(taskManager.getAllTasks().get(id));
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/epic$", path)){
                            final String allTasksToJson = gson.toJson(taskManager.getEpicsPerId());
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/subtask$", path)){
                            final String allTasksToJson = gson.toJson(taskManager.getSubTasksPerId());
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        if(Pattern.matches("^/tasks/subtask/epic/\\d+$", path)){
                            final int id = Integer.parseInt(path.replaceFirst("/tasks/subtask/epic/",""));
                            final String allTasksToJson = gson.toJson(taskManager.getListsOfSubTasks(id));
                            if(!allTasksToJson.isBlank()) {
                                sendText(httpExchange, allTasksToJson);
                                return;
                            } else {
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                        }
                        break;
                    case "POST":
                        if(Pattern.matches("^/tasks/task$", path)){
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), CHARSET_BY_DEFAULT);
                            Task task = gson.fromJson(body, Task.class);
                            httpExchange.sendResponseHeaders(200,0);
                            if(!taskManager.getTasksPerId().containsKey(task.getId())){
                            taskManager.createNewTask(task);
                            System.out.printf("Задача %d создана!\n", task.getId());
                            System.out.println(body);
                            } else {
                                taskManager.updateTask(task, task.getId());
                                System.out.printf("Задача %d обновлена!\n", task.getId());
                                System.out.println(body);
                            }
                            return;
                        }

                        if(Pattern.matches("^/tasks/epic$", path)){
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), CHARSET_BY_DEFAULT);
                            Epic epic = gson.fromJson(body, Epic.class);
                            httpExchange.sendResponseHeaders(200,0);
                            if(!taskManager.getEpicsPerId().containsKey(epic.getId())){
                                taskManager.createNewTask(epic);
                                System.out.printf("Задача %d создана!\n", epic.getId());
                                System.out.println(body);
                            } else {
                                taskManager.updateTask(epic, epic.getId());
                                System.out.printf("Задача %d обновлена!\n", epic.getId());
                                System.out.println(body);
                            }
                            return;
                        }
                        if(Pattern.matches("^/tasks/subtask$", path)){
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), CHARSET_BY_DEFAULT);
                            SubTask subTask = gson.fromJson(body, SubTask.class);
                            httpExchange.sendResponseHeaders(200,0);
                            if(!taskManager.getSubTasksPerId().containsKey(subTask.getId())){
                                taskManager.addNewSubTask(subTask, taskManager.getEpic(subTask.getEpicId()).getSubTaskIds());
                                System.out.printf("Задача %d создана!\n", subTask.getId());
                                System.out.println(body);
                            } else {
                                taskManager.updateTask(subTask, subTask.getId());
                                System.out.printf("Задача %d обновлена!\n", subTask.getId());
                                System.out.println(body);
                            }
                            return;
                        }
                        break;
                    case "DELETE":
                        if(Pattern.matches("^/tasks/task$", path)){
                            taskManager.deleteAllTasks();
                            httpExchange.sendResponseHeaders(200,0);
                            System.out.println("Все задачи удалены!");
                            return;
                        }

                        if(Pattern.matches("^/tasks/task/\\d+$", path)){
                            final int id = Integer.parseInt(path.replaceFirst("/tasks/task/",""));
                            if(taskManager.getTasksPerId().containsKey(id)){
                                taskManager.deleteTaskPerId(id);
                                System.out.printf("Задача %d удалена.\n", id);
                            } else if(taskManager.getEpicsPerId().containsKey(id)){
                                taskManager.deleteEpicPerId(id);
                                System.out.printf("Задача %d удалена.\n", id);
                            } else{
                                taskManager.deleteSubTaskPerId(id);
                                System.out.printf("Задача %d удалена.\n", id);
                            }
                            httpExchange.sendResponseHeaders(200,0);
                            return;
                        }
                        break;
                    default:
                        System.out.println("Произошла ошибка, неизвестный метод: " + method);
                }

            } catch (IOException exception){
                System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                        "Проверьте, пожалуйста, адрес и повторите попытку.");
            } finally {
                httpExchange.close();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        new HttpTaskServer(FileBackedTasksManager.loadFromFile(Path.of("src/tasks.csv").toFile())).start();
    }
}
