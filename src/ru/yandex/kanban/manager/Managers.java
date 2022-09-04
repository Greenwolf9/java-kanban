package ru.yandex.kanban.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.kanban.files.LocalDateTimeAdapter;
import ru.yandex.kanban.server.KVServer;

import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault(){
        return new HttpTaskManager(KVServer.PORT);
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static Gson getGson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
}
