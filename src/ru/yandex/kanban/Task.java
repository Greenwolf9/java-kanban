package ru.yandex.kanban;

public class Task {
    protected String name;
    protected String description;

    protected String status;

    public Task(String name, String description, String status){
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

    public String getStatus(){
        return status;
    }
    public void setStatus(){
        status = "NEW";

    }

    @Override
    public String toString(){
        return "name = " + getName() + ", description = " + getDescription() + ", status = " + getStatus();
    }
}
