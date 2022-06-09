package ru.yandex.kanban;

class SubTask extends Task{
    public Epic epic;
    public SubTask(Epic epic, String name, String description, String status) {
        super(name, description, status);
        this.epic = epic;
    }

    @Override
    public String toString(){
        return "name = " + getName() + ", description = " + getDescription() + ", status = " + getStatus();
    }

}
