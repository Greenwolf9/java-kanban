package ru.yandex.kanban;

public class Task {
    protected String name;
    protected String description;

    protected StatusOfTask status;

    public enum StatusOfTask{
        NEW,
        IN_PROGRESS,
        DONE
    }

    public Task(String name, String description, StatusOfTask status){
        this.name = name;
        this.description = description;
        this.status = status;
    }
    Task(){

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
    public void setStatus(){
        status = StatusOfTask.NEW;

    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = " + getStatus();
    }
}
