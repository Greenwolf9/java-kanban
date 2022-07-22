package ru.yandex.kanban;

public class Task {
    protected String name;
    protected String description;

    protected StatusOfTask status;
    private int id;

    public enum StatusOfTask{
        NEW,
        IN_PROGRESS,
        DONE
    }

    public Task(String name, String description, StatusOfTask status){
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;

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



    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = " + getStatus();
    }
}
