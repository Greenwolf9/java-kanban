package ru.yandex.kanban.tasks;

public class Task {
    protected String name;
    protected String description;

    public StatusOfTask status;
    protected int id;

    protected TaskType type;

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
    Task(){

    }
    public int getId(){
        return id;
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




    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus() + ", type = " + getType() + ", id = " + getId();
    }
}
