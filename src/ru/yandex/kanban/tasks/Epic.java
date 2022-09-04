package ru.yandex.kanban.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{

    protected List<Integer> subTaskIds;

    protected LocalDateTime endTime;

    public Epic(String name, String description, TaskType type){
        this.name = name;
        this.description = description;
        this.type = type;
        subTaskIds = new ArrayList<>();
        this.status = getStatus();
        this.id = getId();
        this.startTime = getStartTime();
        this.endTime = getEndTime();
        this.duration = getDuration();
    }




    public List<Integer> getSubTaskIds(){
        return subTaskIds;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus() + ", type = " + getType() + ", id = " + getId() + ", startTime = "
                + getStartTime().format(formatter) + ", duration = " + getDuration() + " min"
                + ", endTime = " + getEndTime().format(formatter);
    }
}
