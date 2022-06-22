package ru.yandex.kanban;

public class Managers {
    public static TaskManager getDefault(HistoryManager historyManager){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
