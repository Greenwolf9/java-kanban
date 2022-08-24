package ru.yandex.kanban.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;

    public StatusOfTask status;
    protected int id;

    protected TaskType type;
    protected LocalDateTime startTime;



    protected long duration;

    public enum StatusOfTask{
        NEW,
        IN_PROGRESS,
        DONE
    }
    public enum TaskType {
        TASK,
        EPIC,
        SUBTASK;
    }

    public Task(String name, String description, StatusOfTask status, TaskType type){
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = getId();
        this.type = type;

    }
    public Task(){

    }

    public Task(String name, String description,
                StatusOfTask status, TaskType type,
                LocalDateTime startTime, long duration)
    {
        this.id = getId();
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;

    }
    public int getId(){
        return id;
    }

    public void setStatus(StatusOfTask status){
        this.status = status;
    }
    public void setId(int id){
        this.id = id;
    }


    public String getName(){
        return name;
    }


    public String getDescription(){
        return description;
    }

    public StatusOfTask getStatus(){
        return status;
    }
    public TaskType getType(){
        return type;
    }

    public LocalDateTime getStartTime(){

        return startTime;
    }
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public long getDuration(){
        return duration;
    }

    public void setDuration(long duration){
        this.duration = duration;
    }

    public LocalDateTime getEndTime(){
        return startTime.plusMinutes(duration);
    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status && type == task.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, status, type);
    }



    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus() + ", type = " + getType() + ", id = " + getId() + ", startTime = "
                + getStartTime().format(formatter) + ", duration = " + getDuration() + " min"
                + ", endTime = " + getEndTime().format(formatter);
    }
}
