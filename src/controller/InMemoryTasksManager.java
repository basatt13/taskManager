package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class InMemoryTasksManager implements TaskManager {
    HashMap<Integer, Task> allTasks = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();
    HashMap<Integer, SubTask> allSubTusk = new HashMap<>();
    List<Task> historyList = new ArrayList<>();


    @Override
    public void createTask(HashMap<Integer, Task> allTasks) {
        System.out.println("Введите название задачи");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberTask = Task.generateNumberTask(allTasks);
        Status status = Status.NEW;
        Task task = new Task(numberTask, name, details, status);
        Task.addTasks(task, allTasks);
    }

    @Override
    public void createEpic (HashMap<Integer, Epic> allEpics) {
        System.out.println("Введите название эпика");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberEpics = Epic.generateNumberEpics(allEpics);
        Status status = Status.NEW;
        Epic epic = new Epic(numberEpics, name, details, status);
        Epic.addEpics(epic, allEpics);
    }

    @Override
    public void createSubtask(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner scanner = new Scanner(System.in);
        if (allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            showListEpics(allEpics);
            System.out.println("Введите номер эпика для которого создается подзадача");
            int idEpic = scanner.nextInt();
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите название подзадачи ");
            String name = sc.nextLine();
            System.out.println("Введите описание подзадачи ");
            String details = sc.nextLine();
            int numberSubtask = SubTask.generateNumberSub(allSubTusk);
            Status status = Status.NEW;
            SubTask subTask = new SubTask(numberSubtask, name, details, status);
            SubTask.addSubtask(subTask, allSubTusk);
            allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
        }
    }

    @Override
    public void showListTasks(HashMap<Integer, Task> allTasks) {
        System.out.println("Список всех задач");
        for (Task task : allTasks.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public void showListEpics(HashMap<Integer, Epic> allEpics) {
        System.out.println("Список всех эпиков'");
        for (Epic epic : allEpics.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void showListSubtask(HashMap<Integer, SubTask> allSubTusk) {
        System.out.println("Список всех подзадач'");
        for (int epic : allSubTusk.keySet()) {
            System.out.println(allSubTusk.get(epic).toString());
        }
    }

    @Override
    public void getListSubtasksByEpicID(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner sc = new Scanner(System.in);
        showListEpics(allEpics);
        System.out.println("Введите номер эпика для которого нужно получить список всех подзадач");
        int idEpic = sc.nextInt();
        for (SubTask k : allSubTusk.values()) {
            if (k.getEpic() == idEpic) {
                System.out.println(k.toString());
            }
        }
    }

    @Override
    public void getUpdateByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                     HashMap<Integer, SubTask> allSubTusk) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи обновить");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            Task.updateTask(allTasks);
        } else if (numType == 2) {
            Epic.updateEpic(allEpics);
        } else if (numType == 3) {
            SubTask.updateSubtask(allSubTusk);
        }
    }

    @Override
    public void getAnyByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                  HashMap<Integer, SubTask> allSubTusk, List<Task> historyList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи показать");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            Task.getTaskByID(allTasks);
        } else if (numType == 2) {
            Epic.getEpicByID(allEpics,historyList);
        } else if (numType == 3) {
            SubTask.getSubtaskByID(allSubTusk,historyList);
        }
    }

    @Override
    public void removeTaskByID(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        showListTasks(allTasks);
        System.out.println("Введите ID задачи, которую необходимо удалить");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                allTasks.remove(ID);
            }
        }
    }

    @Override
    public void removeAllTask(HashMap<Integer, Task> allTasks) {
        allTasks.clear();
    }

    @Override
    public void updateStatusEpic(HashMap<Integer, Epic> allEpics, HashMap<Integer, SubTask> allSubTusk) {
        for (Epic k : allEpics.values()) {
            int countStatusNew = 0;
            int countStatusDone = 0;
            int countStatusProgress = 0;
            if (!k.getSubtasks().isEmpty()) {
                for (Integer j : k.getSubtasks()) {
                    if (allSubTusk.get(j).getStatus().equals(Status.NEW)) {
                        countStatusNew += 1;
                    } else if (allSubTusk.get(j).getStatus().equals(Status.DONE)) {
                        countStatusDone += 1;
                    } else if (allSubTusk.get(j).getStatus().equals(Status.TO_PROGRESS)) {
                        countStatusProgress += 1;
                    }
                }
            }
            System.out.println(countStatusDone + countStatusNew + countStatusProgress);
            if (countStatusNew == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.NEW);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusDone == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.DONE);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusProgress >= 1) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.TO_PROGRESS);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
        showListEpics(allEpics);
    }

    @Override
    public void history(){
        for (Task t: historyList){
            System.out.println(t.toString());
        }
    }
}






