package ru.yandex.kanban;

import java.util.ArrayList;
import java.util.List;

class Epic extends Task{

    private final List<Integer> subTaskIds;

    public Epic(String name, String description){
        this.name = name;
        this.description = description;
        subTaskIds = new ArrayList<>();
        this.status = getStatus();
    }
    public List<Integer> getSubTaskIds(){
        return subTaskIds;
    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus();
    }
}
