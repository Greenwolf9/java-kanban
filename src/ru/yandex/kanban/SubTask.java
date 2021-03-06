package ru.yandex.kanban;

class SubTask extends Task{
    public Epic epic;
    private int id;

    public SubTask(Epic epic, String name, String description, StatusOfTask status) {
        super(name, description, status);
        this.epic = epic;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "\nname = " + getName() + ", description = " + getDescription() + ",\nstatus = " + getStatus();
    }

}
