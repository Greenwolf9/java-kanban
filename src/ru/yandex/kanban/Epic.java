package ru.yandex.kanban;

import java.util.ArrayList;
import java.util.List;

class Epic extends Task{

    private List<SubTask> listsOfSubTasks;
    StatusOfTask status;
    public Epic(String name, String description){
        this.name = name;
        this.description = description;
        listsOfSubTasks = new ArrayList<>();
        this.status = getStatus();
    }
    public List<SubTask> getListsOfSubTasks(){
        return listsOfSubTasks;
    }
    public void setStatus(){
        findStatusOfEpic(listsOfSubTasks);
    }
    public String findStatusOfEpic(List<SubTask> listsOfSubTasks){
        int countNew = 0;
        int countDone = 0;
        if (!listsOfSubTasks.isEmpty()) {
            for(SubTask list: listsOfSubTasks){
                if (list.status.equals(StatusOfTask.NEW)){
                    countNew++;
                } else if(list.status.equals(StatusOfTask.DONE)){
                    countDone++;
                }
                if (countNew == listsOfSubTasks.size()){
                    status = StatusOfTask.NEW;
                } else if (countDone == listsOfSubTasks.size()){
                    status = StatusOfTask.DONE;
                } else {
                    status = StatusOfTask.IN_PROGRESS;
                }
            }
        } return status.name();
    }


    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + findStatusOfEpic(listsOfSubTasks);
    }

    }
