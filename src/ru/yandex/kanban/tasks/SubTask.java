package ru.yandex.kanban.tasks;


import java.time.LocalDateTime;

public class SubTask extends Task{
    public Epic epic;
    protected int id;
    protected int epicId;


    public SubTask(Epic epic, String name, String description, StatusOfTask status, TaskType type) {
        super(name, description, status, type);
        this.epic = epic;
        this.id = getId();

    }

    public SubTask(Epic epic, String name, String description,
                   StatusOfTask status, TaskType type,
                   LocalDateTime startTime, long duration){
        super(name, description, status, type, startTime, duration);
        this.id = getId();
        this.epic = epic;
    }

    public int getEpicId(){
        return epicId;
    }

    public void setEpicId(){
        this.epicId = epic.getId();
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
                + getStatus() + ", type = " + getType() + ", id = " + getId() + ", startTime = "
                + getStartTime().format(formatter) + ", duration = " + getDuration() + " min"
                + ", endTime = " + getEndTime().format(formatter);
    }

}
