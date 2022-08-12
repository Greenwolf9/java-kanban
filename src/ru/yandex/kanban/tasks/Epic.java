package ru.yandex.kanban.tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{

    protected List<Integer> subTaskIds;
    protected int id;

    public Epic(String name, String description, TaskType type){
        this.name = name;
        this.description = description;
        this.type = type;
        subTaskIds = new ArrayList<>();
        this.status = getStatus();
        this.id = getId();
    }
    public Epic(int epicId){
        this.id = epicId;
    }

    public int getId(){
        return id;
    }


    public void setId(int id){
        this.id = id;
    }
    public List<Integer> getSubTaskIds(){
        return subTaskIds;
    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus() + ", type = " + getType() + ", id = " + getId();
    }
}
