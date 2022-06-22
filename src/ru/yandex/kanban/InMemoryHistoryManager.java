package ru.yandex.kanban;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    List<Task> historyOfLastTenTask = new ArrayList<>();

    public void addTask(Task task){
        historyOfLastTenTask.add(historyOfLastTenTask.size(), task);
        if (historyOfLastTenTask.size() > 10){
            historyOfLastTenTask.remove(0);
        }
    }

    public List<Task> getHistory(){
        System.out.println(historyOfLastTenTask);
        return historyOfLastTenTask;
    }
}
