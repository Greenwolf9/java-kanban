package ru.yandex.kanban.test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import ru.yandex.kanban.manager.InMemoryTaskManager;
import ru.yandex.kanban.manager.TaskManager;
import ru.yandex.kanban.tasks.Epic;
import ru.yandex.kanban.tasks.SubTask;
import ru.yandex.kanban.tasks.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest <T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected SubTask subTask;
    protected SubTask subTask2;


    public void createTasksForTest(){

        epic = new Epic("Эпик 1",
                "подробное описание", Task.TaskType.EPIC);

        subTask = new SubTask(1,"Подзадача 3 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 12)), 40);
        subTask2 = new SubTask(1,"Подзадача 4 для эпика 1",
                "Подробное описание",
                Task.StatusOfTask.DONE, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 20)), 40);
        task = new Task("Задача 2", "подробное описание", Task.StatusOfTask.NEW,
                Task.TaskType.TASK, LocalDateTime.now(), 60);

        taskManager.createNewEpic(epic);
        taskManager.createNewTask(task);
        taskManager.addNewSubTask(subTask, epic.getSubTaskIds());
        taskManager.addNewSubTask(subTask2, epic.getSubTaskIds());
        taskManager.getTask(task.getId());
        taskManager.getEpic(epic.getId());
        taskManager.getSubTask(subTask.getId());


    }

    @Test
    void addNewTask() {

        final Task savedTask = taskManager.getTask(task.getId());
        assertNotNull(savedTask, "Task not found.");
        assertEquals(task, savedTask, "Tasks are not equal.");
        final Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertNotNull(tasks, "List is empty.");
        assertEquals(1, tasks.size(), "Wrong quantity of tasks.");
        assertEquals(task, tasks.get(2), "Tasks are not equal.");
    }

    @Test
    void addNewEpic() {

        final Epic savedEpic = taskManager.getEpic(epic.getId());
        assertNotNull(savedEpic, "Task not found.");
        assertEquals(epic, savedEpic, "Tasks are not equal.");
        final Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        assertNotNull(epics, "List is empty.");
        assertEquals(1, epics.size(), "Wrong quantity of tasks.");
        assertEquals(epic, epics.get(1), "Tasks are not equal.");
    }

    @Test
    void addNewSubTask() {

        final SubTask savedSubTask = taskManager.getSubTask(subTask.getId());
        assertNotNull(savedSubTask, "Task not found.");
        assertEquals(subTask, savedSubTask, "Tasks are not equal.");
        final Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertNotNull(subtasks, "List is empty.");
        assertEquals(2, subtasks.size(), "Wrong quantity of tasks.");
        assertEquals(subTask, subtasks.get(3), "Tasks are not equal.");
    }

    @Test
    void getListsOfSubTasks() {
        List<SubTask> listsOfSubTasks = taskManager.getListsOfSubTasks(epic.getId());
        assertFalse(listsOfSubTasks.isEmpty());
        assertEquals(2, listsOfSubTasks.size(), "Wrong list of subtasks");
    }

    @Test
    void shouldFindStatusOfEpicIfInProgress() {
        Epic epicForTest = taskManager.getEpic(epic.getId());
        assertEquals(Task.StatusOfTask.IN_PROGRESS, epicForTest.getStatus(), "Wrong status of Epic");

    }

    @Test
    void shouldFindStatusOfEpicIfAllSubTasksNew() {
        Epic epicForTest = taskManager.getEpic(epic.getId());
        assertEquals(Task.StatusOfTask.IN_PROGRESS, epicForTest.getStatus());
        subTask2.setStatus(Task.StatusOfTask.NEW);
        taskManager.findStatusOfEpic(epicForTest);
        assertEquals(Task.StatusOfTask.NEW, epicForTest.getStatus(), "Wrong status of Epic");
    }

    @Test
    void shouldFindStatusOfEpicIfAllSubTasksDONE() {
        Epic epicForTest = taskManager.getEpic(epic.getId());
        assertEquals(Task.StatusOfTask.IN_PROGRESS, epicForTest.getStatus());
        subTask.setStatus(Task.StatusOfTask.DONE);
        taskManager.findStatusOfEpic(epicForTest);
        assertEquals(Task.StatusOfTask.DONE, epicForTest.getStatus(), "Wrong status of Epic");
    }

    @Test
    void shouldFindStatusOfEpicWithNoSubtasks() {
        Epic epicForTest = taskManager.getEpic(epic.getId());
        assertEquals(Task.StatusOfTask.IN_PROGRESS, epicForTest.getStatus());
        List<Integer> listsOfSubTasksIds = epicForTest.getSubTaskIds();
        listsOfSubTasksIds.clear();
        taskManager.findStatusOfEpic(epicForTest);
        assertEquals(Task.StatusOfTask.NEW, epicForTest.getStatus(), "Wrong status of Epic");
    }
    @Test
    void shouldFindStartTimeAndDurationOfEpic(){
        SubTask subTaskForTest = new SubTask(1, "Подзадача 5 для эпика 1", "Подробное описание",
                Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 20)), 20);

        Epic epicForTest = taskManager.getEpic(epic.getId());
        assertNotNull(epicForTest.getStartTime());
        assertNotNull(epicForTest.getDuration());
        assertNotNull(epicForTest.getEndTime());
        assertEquals(epic.getStartTime(), epicForTest.getStartTime());
        assertEquals(epic.getDuration(), epicForTest.getDuration());
        assertEquals(epic.getEndTime(), epicForTest.getEndTime());
        taskManager.addNewSubTask(subTaskForTest, epic.getSubTaskIds());
        assertEquals(epic.getStartTime(), epicForTest.getStartTime());
        assertEquals(epic.getDuration(), epicForTest.getDuration());
        assertEquals(epic.getEndTime(), epicForTest.getEndTime());

    }

    @Test
    void shouldDeleteTaskPerId() {
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertEquals(1, tasks.size());
        taskManager.deleteTaskPerId(task.getId());
        assertEquals(0, tasks.size(), "Task wasn't deleted");
    }
    @Test
    void shouldNotDeleteTaskPerIdIfWrongId() {
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertEquals(1, tasks.size());
        taskManager.deleteTaskPerId(epic.getId());
        assertEquals(1, tasks.size(), "Task wasn't deleted");
    }

    @Test
    void shouldDeleteSubTaskPerId() {
        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertEquals(2, subtasks.size());
        taskManager.deleteSubTaskPerId(subTask.getId());
        assertEquals(1, subtasks.size(), "SubTask wasn't deleted");
    }

    @Test
    void shouldNotDeleteSubTaskPerIdIfWrongId() {
        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertEquals(2, subtasks.size());
        taskManager.deleteSubTaskPerId(task.getId());
        assertEquals(2, subtasks.size(), "SubTask wasn't deleted");
    }

    @Test
    void shouldDeleteEpicPerId() {
        Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        assertEquals(1, epics.size());
        taskManager.deleteEpicPerId(epic.getId());
        assertEquals(0, epics.size(), "Epic wasn't deleted");
    }

    @Test
    void shouldNotDeleteEpicPerIdIfWrongId() {
        Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        assertEquals(1, epics.size());
        taskManager.deleteEpicPerId(subTask.getId());
        assertEquals(1, epics.size(), "Epic wasn't deleted");
    }

    @Test
    void shouldDeleteAllTasks() {
        Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertEquals(1, epics.size());
        assertEquals(1, tasks.size());
        assertEquals(2, subtasks.size());
        taskManager.deleteAllTasks();
        assertTrue(epics.isEmpty());
        assertTrue(tasks.isEmpty());
        assertTrue(subtasks.isEmpty());
        assertEquals(0, epics.size(), "Epics were not deleted");
        assertEquals(0, tasks.size(), "Tasks were not deleted");
        assertEquals(0, subtasks.size(), "Subtasks were not deleted");
    }

    @Test
    void shouldUpdateTasks() {
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertEquals(task, tasks.get(task.getId()));
        task.setStatus(Task.StatusOfTask.DONE);
        taskManager.updateTask(task, task.getId());
        assertEquals(task, tasks.get(task.getId()), "Task is not up to date");

    }

    @Test
    void shouldReturnTaskPerId() {
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        Task taskForComparison = tasks.get(task.getId());
        assertNotNull(taskForComparison);
        assertEquals(task, taskForComparison, "Tasks are not equal");

    }

    @Test
    void shouldReturnEpicPerId() {
        Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        assertEquals(1,epics.size(), "Map is empty");
        Epic epicForComparison = epics.get(epic.getId());
        assertNotNull(epicForComparison);
        assertEquals(epic, epicForComparison, "Epics are not equal");
    }

    @Test
    void shouldReturnSubTaskPerId() {
        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertEquals(2, subtasks.size(), "Map is empty");
        SubTask subTaskForComparison = subtasks.get(subTask.getId());
        assertNotNull(subTaskForComparison);
        assertEquals(subTask, subTaskForComparison, "SubTasks are not equal");
    }

    @Test
    void shouldReturnAllTasks(){
        Map<Integer, Task> allTasks = taskManager.getAllTasks();
        assertNotNull(allTasks);
        assertEquals(4, allTasks.size(), "The quantity of created tasks is wrong");

    }


    @Test
    public void shouldCheckIfEpicsAreInMap(){
        Map<Integer, Epic> epics = taskManager.getEpicsPerId();
        assertFalse(epics.isEmpty(), "Map is empty");
        assertEquals(1, epics.size(), "There is no epic in the map");
    }


    @Test
    public void shouldCheckIfTasksAreInMap(){
        Map<Integer, Task> tasks = taskManager.getTasksPerId();
        assertFalse(tasks.isEmpty(), "Map is empty");
        assertEquals(1, tasks.size(), "There is no task in the map");
    }

    @Test
    public void shouldCheckIfSubTasksAreInMap(){
        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        assertFalse(subtasks.isEmpty(), "Map is empty");
        assertEquals(2, subtasks.size(), "There is no subtask in the map");
    }

    @Test
    public void shouldCheckIfSubTaskKnowAboutItsEpic(){

        Map<Integer, SubTask> subtasks = taskManager.getSubTasksPerId();
        final int epicIdForTest = subtasks.get(subTask.getId()).getEpicId();
        assertNotNull(epicIdForTest);
        assertEquals(subTask.getEpicId(), epicIdForTest);

    }

    @Test
    public void getPrioritizedTasks(){
        TreeSet<Task> priorityTasks = taskManager.getPrioritizedTasks();
        assertNotNull(priorityTasks);
        assertEquals(3, priorityTasks.size());
    }

    @Test
    public void shouldCheckOverlappingTasks() {
        SubTask subTaskForTest = new SubTask(1,"Подзадача 5 для эпика 1",
                "Подробное описание", Task.StatusOfTask.NEW, Task.TaskType.SUBTASK,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 32)), 40);

        InMemoryTaskManager.TaskOverlappingException thrown = assertThrows(
                InMemoryTaskManager.TaskOverlappingException.class,
                ()->

                    taskManager.checkOverlappingTasks(subTaskForTest),
                "Expected addNewSubTask() to throw, but it didn't");

        assertEquals("Нельзя выполнять несколько задач одновременно. " +
                "Выполнение задачи " + subTaskForTest.getName() + " необходимо перенести на другое время.",
                thrown.getMessage());
    }

}