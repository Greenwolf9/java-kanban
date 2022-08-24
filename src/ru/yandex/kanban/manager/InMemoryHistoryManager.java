package ru.yandex.kanban.manager;

import ru.yandex.kanban.tasks.*;

import java.util.*;
import java.util.Collections;

public class InMemoryHistoryManager implements HistoryManager{

    Map<Integer, Node<Task>> historyInNodes = new LinkedHashMap<>();
    private Node<Task> tail;
    private Node<Task> head;


    public void addLast(Task task){ // привязка задачи к последнему узлу
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if(oldTail == null){
            head = newNode;
        } else{
            oldTail.next = newNode;
        }

    }
    public Node<Task> getLast() {  // геттер последнего узла
        final Node<Task> curTail = tail;
        if (curTail == null)
            throw new NoSuchElementException();
        return tail;
    }
    @Override
    public void addTask(Task task){  // добавление задачи в связный список

        if (task == null){
            return;
        }
        final int id = task.getId();
        remove(id);
        addLast(task);
        historyInNodes.put(id,getLast());
    }

    @Override
    public List<Task> getHistory(){  // список истории
        List<Task> historyOfTasks = new ArrayList<>();
        for(Node<Task> node: historyInNodes.values()){
        historyOfTasks.add(node.data);
        }
        Collections.reverse(historyOfTasks);
        //System.out.println(historyOfTasks);
        return historyOfTasks;
    }


    @Override
    public void remove(int id){  // удаление задачи из просмотра по айди
        Node<Task> task = historyInNodes.get(id);
        Node<Task> current = head;
        while(current != null && current != task){
            current = current.next;
        }
        historyInNodes.remove(id);
        removeNode(task);
    }

    private void removeNode(Node node){ // перепривязка удаленной ноды
        if (node != null){
            if (node.prev != null){
                node.prev.next = node.next;
            }
            else {
                head = node.next;}

            if (node.next != null){
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        }
    }


    private static class Node<Task>{
        private Task data;
        private Node<Task> next;
        private Node<Task> prev;

        private Node(Node<Task> prev, Task data, Node<Task> next){
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

}

