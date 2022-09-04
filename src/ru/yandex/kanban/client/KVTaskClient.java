package ru.yandex.kanban.client;

import com.google.gson.Gson;
import ru.yandex.kanban.manager.Managers;
import ru.yandex.kanban.server.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {

    private String apiToken;
    private String url;
    private Gson gson;



    public KVTaskClient(int port) {
        url = "http://localhost:" + port;
        apiToken = registerApiToken(url);
        gson = Managers.getGson();

    }

    private String registerApiToken(String url) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiToken = response.body();
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
        return apiToken;
    }

    public void put(String key, String json) {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.statusCode());

    }

    public String load(String key) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        KVServer kvServer = new KVServer();
        kvServer.start();
        KVTaskClient kvTaskClient = new KVTaskClient(KVServer.PORT);
        kvTaskClient.put("/tasks", "{name:John,age:28}");
        kvTaskClient.load("/tasks");
        kvServer.stop();
    }

}
