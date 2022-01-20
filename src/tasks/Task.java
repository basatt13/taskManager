package tasks;
import controller.Managers;
import controller.Status;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Task {
    int ID;
    private String name;
    private String details;
    private Status status;

    public Task(int ID, String name, String details, Status status) {
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Задача{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getID() == task.getID() && Objects.equals(getName(), task.getName()) &&
                Objects.equals(getDetails(), task.getDetails()) &&
                Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        Objects.hash(getID(), getName(), getDetails());
        int result = Objects.hash(getID(),getName(),getDetails());
        result = result*31;
        return result;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public Status getStatus() {
        return status;
    }

    public static int generateNumberTask(HashMap<Integer, Task> allTasks) {
        return allTasks.size() + 1;
    }

    public static void updateTask(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListTasks(allTasks);
        System.out.println("Введите ID задачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название задачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание задачи ");
                String details = scan.nextLine();
                Task task = new Task(ID, name, details, k.getStatus());
                addTasks(task, allTasks);
            }
        }
    }

    public static void addTasks(Task o, HashMap<Integer, Task> allTasks) {
        allTasks.put(o.getID(), o);
    }

    public static void getTaskByID(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID задачи. которую необходимо показать");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
            }
        }
    }
}


