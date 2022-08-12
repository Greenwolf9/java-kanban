package ru.yandex.kanban.tasks;


public class SubTask extends Task{
    public Epic epic;
    protected int id;


    public SubTask(Epic epic, String name, String description, StatusOfTask status, TaskType type) {
        super(name, description, status, type);
        this.epic = epic;
        this.id = getId();

    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = "
                + getStatus() + ", type = " + getType() + ", id = " + getId();
    }

}
