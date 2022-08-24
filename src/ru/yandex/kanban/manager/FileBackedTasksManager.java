package ru.yandex.kanban.manager;
import ru.yandex.kanban.files.CsvFileFormatter;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;
import java.io.IOException;

import java.io.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{

    protected final Path file;
    public FileBackedTasksManager() {
        file = Paths.get("src/tasks.csv");
    }

    @Override
    public void createNewTask(Task task){
        super.createNewTask(task);
        save();
    }
    @Override
    public Task getTask(int id){
        super.getTask(id);
        save();
        return super.getTask(id);
    }

    @Override
    public void createNewEpic(Epic epic){
        super.createNewEpic(epic);
        save();
    }
    @Override
    public Epic getEpic(int id){
        super.getEpic(id);
        save();
        return super.getEpic(id);
    }

    @Override
    public void addNewSubTask(SubTask subTask, List<Integer> subTasksIds){
        super.addNewSubTask(subTask, subTasksIds);
        save();
    }
    @Override
    public SubTask getSubTask(int id){
        super.getSubTask(id);
        save();
        return super.getSubTask(id);
    }

    public List<Task> getHistory(){

        return super.inMemoryHistoryManager.getHistory();
    }

    private void save() {                                           // сохраняем в файл
        try (FileWriter writer = new FileWriter(file.toFile())){
            String firstLine = "id, type, name, status, description, epic\n";
            writer.write(firstLine);

            for(Task task: getTasksPerId().values()){
                writer.write(CsvFileFormatter.toString(task) + System.lineSeparator());
            }
            for(Epic epic: getEpicsPerId().values()){
                writer.write(CsvFileFormatter.epicToString(epic) + System.lineSeparator());
            }
            for(SubTask subTask: getSubTasksPerId().values()){
                writer.write(CsvFileFormatter.subTaskToString(subTask) + System.lineSeparator());

            }
            writer.write(System.lineSeparator());
            if (CsvFileFormatter.historyToString(getInMemoryHistoryManager())==null){
                throw new ManagerSaveException("Упс. Что-то пошло не так при записи файла.");
            } else{
            writer.write(CsvFileFormatter.historyToString(getInMemoryHistoryManager()));
            }

        } catch(ManagerSaveException | IOException exception) {
            exception.getMessage();
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {  // создаем задачи, запрашиваем их, записываем и считываем
        FileBackedTasksManager backedTasksManager = new FileBackedTasksManager();
        try{
        backedTasksManager.read(file);}
        catch (IOException exception){
            exception.getMessage();
        }
        return backedTasksManager;
    }

    protected void read(File file) throws IOException{  // считываем файл

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            boolean firstLine = true;
            while(br.ready()) {
                String lines = br.readLine();
                if (firstLine) {
                    firstLine = false;
                    continue;
                } else {
                    if (!lines.isBlank()) {

                        if (CsvFileFormatter.fromString(lines).getType().equals(Task.TaskType.TASK)) {
                            Task task = CsvFileFormatter.fromString(lines);
                            tasksPerId.put(task.getId(), task);
                        } else if (CsvFileFormatter.fromString(lines).getType().equals(Task.TaskType.EPIC)) {

                            Epic epic = (Epic)CsvFileFormatter.fromString(lines);
                            epicsPerId.put(epic.getId(), epic);
                        } else if (CsvFileFormatter.fromString(lines).getType().equals(Task.TaskType.SUBTASK)) {
                            SubTask subTask = (SubTask) CsvFileFormatter.fromString(lines);
                            subTasksPerId.put(subTask.getId(), subTask);
                            Epic epic = epicsPerId.get(subTask.epic.getId());
                            epic.getSubTaskIds().add(subTask.getId());
                            findStartTimeAndDurationOfEpic(epic);

                        }
                    } else {
                        String line = br.readLine();
                        if(line==null){
                            throw new ManagerSaveException("История не заполнена.");
                        }
                        StringBuilder sb = new StringBuilder(line);
                        sb.reverse();
                        String newLine = sb.toString();
                        for (Integer key : CsvFileFormatter.historyFromString(newLine)) {
                            if (tasksPerId.containsKey(key)) {
                                getTask(key);
                            } else if (epicsPerId.containsKey(key)) {
                                getEpic(key);
                            } else if (subTasksPerId.containsKey(key)) {
                                getSubTask(key);
                            }
                        }
                    }
                }
            }
        } catch (ManagerSaveException | IOException exception){
            System.out.println("Упс. Что-то пошло не так при чтении файла. " + exception.getMessage());
        }
        System.out.println(getHistory());
    }

    public class ManagerSaveException extends Exception {
        public ManagerSaveException(final String message) {
            super(message);
        }
    }

    public static void main(String[] args) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        Epic epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);

       SubTask subTask = new SubTask(epic,"Подзадача 3 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
               LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 12)), 40);
        SubTask subTask2 = new SubTask(epic,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 20)), 40);
        Task task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW,
                Task.TaskType.TASK, LocalDateTime.now(), 60);

        fileBackedTasksManager.createNewEpic(epic);
        fileBackedTasksManager.createNewTask(task);
        fileBackedTasksManager.addNewSubTask(subTask, epic.getSubTaskIds());
        fileBackedTasksManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        fileBackedTasksManager.getEpic(epic.getId());
        fileBackedTasksManager.getTask(task.getId());
        fileBackedTasksManager.getSubTask(subTask.getId());
        fileBackedTasksManager.getSubTask(subTask2.getId());
        FileBackedTasksManager newOneFileBackedManager = loadFromFile(new File("src/tasks.csv"));
        newOneFileBackedManager.getHistory();
    }
}
