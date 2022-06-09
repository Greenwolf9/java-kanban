package ru.yandex.kanban;

import java.util.ArrayList;

class Epic extends Task{


    public ArrayList<SubTask> listsOfSubTasks;
    String status;
    public Epic(String name, String description){
        this.name = name;
        this.description = description;
        listsOfSubTasks = new ArrayList<>();
        this.status = getStatus();
    }
    public ArrayList<SubTask> getListsOfSubTasks(){
        return listsOfSubTasks;
    }
    public void setStatus(){
        findStatusOfEpic(listsOfSubTasks);
    }
    public String findStatusOfEpic(ArrayList<SubTask> listsOfSubTasks){
        int countNew = 0;
        int countDone = 0;
        if (!listsOfSubTasks.isEmpty()) {
            for(SubTask list: listsOfSubTasks){
                if (list.status.equals("NEW")){
                    countNew++;
                } else if(list.status.equals("DONE")){
                    countDone++;
                }
                if (countNew == listsOfSubTasks.size()){
                    status = "NEW";
                } else if (countDone == listsOfSubTasks.size()){
                    status = "DONE";
                } else {
                    status = "IN PROGRESS";
                }
            }
        } return status;
    }

    @Override
    public String toString(){
        return "name = " + getName() + ", description = " + getDescription() + ", status = " + findStatusOfEpic(listsOfSubTasks) +
                ", listOfSubTasks = " + getListsOfSubTasks();
    }
    }
