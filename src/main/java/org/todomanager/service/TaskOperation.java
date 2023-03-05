package org.todomanager.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.todomanager.model.TaskData;
import org.todomanager.model.UserData;

import java.util.List;
import java.util.Scanner;

/**
 * As a user I should be able to log in and system should validate the credentials -- Done
 * As a user I should be able to add the task into a list -- Done
 * As a user I should be able to update the task in the list -- Done
 * As a user I should be able to delete the task in the list -- Done
 * As a user I should be able to search a task from a list --
 * As a user I should be able to see all my tasks -- Done
 * As a suer I should be able to see all the completed or incomplete tasks based on the requirement -- Done
 */
public class TaskOperation implements TaskManagement{

    static Scanner in = new Scanner(System.in);

    static int choice;

    public void updateTaskMenu(Session session, TaskData task)
    {
        boolean updateTaskSubMenu = true;
        while(updateTaskSubMenu)
        {
            System.out.println("\n**************************** Select Update Task Menu **********************************\n" +
                    "1. Update Task Title\n" +
                    "2. Update Task Description\n" +
                    "3. Update Status\n" +
                    "4. Exit");
            choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter new Task Title");
                    String taskTitle = in.next();
                    task.setTaskTitle(taskTitle);
                }
                case 2 -> {
                    System.out.println("Enter new Task Description");
                    String taskDesc = in.next();
                    task.setTaskText(taskDesc);
                }
                case 3 -> {
                    System.out.println("If Completed Enter Y or N");
                    boolean isCompleted = in.nextBoolean();
                    task.setTaskCompleted(isCompleted);
                }
                case 4 -> {
                    updateTaskSubMenu = false;
                    break;
                }
                default -> System.out.println("Please Enter Correct Value");
            }
        }
    }

    /**
     *
     */
    @Override
    public void showAllTask(Session session, UserData user) {
        try
        {
            List<TaskData> allTask = session.createQuery("from TaskData where user.id=:id", TaskData.class)
                    .setParameter("id", user.getId()).getResultList();
            if(!allTask.isEmpty())
            {
                for (TaskData x: allTask) {
                    System.out.println(x);
                }
            }
            else
            {
                System.out.println("No Task Added So far! Please Add some task");
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void addTask(Session session, String taskTitle, String taskText, UserData user, boolean isCompleted) {
        try {
            TaskData checkTask = session.createQuery("from TaskData where user.id=:id AND TaskTitle=:taskTitle and taskCompleted=false", TaskData.class)
                    .setParameter("id", user.getId())
                    .setParameter("taskTitle", taskTitle).uniqueResult();

            if(checkTask!=null)
            {
                System.out.println("The same Task for the User:"+ user.getUsername() +" with status incomplete already Exist!");
            }
            else
            {
                TaskData task = new TaskData(taskTitle, taskText, user.getEmail(), false);
                session.persist(task);
                task.setUser(user);
            }

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void updateTask(Session session, UserData loggedUser) {
        try
        {
            boolean updateTaskMenu = true;
            TaskData taskUpdate;

            List<TaskData> task = session.createQuery("from TaskData where user.id=:id", TaskData.class)
                    .setParameter("id", loggedUser.getId()).getResultList();

            if(task.isEmpty())
            {
                System.out.println("No Task Assigned So Far! Please Add Some Task");
            }
            else {
                showAllTask(session, loggedUser);
                while (updateTaskMenu) {
                    System.out.println("Select Options\n" +
                            "1. Update via Task Id\n" +
                            "2. Update via Task Title\n" +
                            "3. Exit");

                    choice = in.nextInt();

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter Task Id");
                            long taskId = in.nextLong();
                            taskUpdate = session.createQuery("from TaskData where user.id=:id AND id=:task_Id", TaskData.class)
                                    .setParameter("id", loggedUser.getId())
                                    .setParameter("task_Id", taskId).uniqueResult();
                            updateTaskMenu(session, taskUpdate);
                            session.merge(taskUpdate);
                        }
                        case 2 -> {
                            System.out.println("Enter Task Title");
                            String taskTitle = in.next();
                            taskUpdate = session.createQuery("from TaskData where user.id=:id AND TaskTitle like :taskTitle", TaskData.class)
                                    .setParameter("id", loggedUser.getId())
                                    .setParameter("taskTitle", "%" + taskTitle + "%").uniqueResult();
                            updateTaskMenu(session, taskUpdate);
                            session.merge(taskUpdate);
                        }
                        case 3 -> {
                            updateTaskMenu = false;
                        }
                        default -> System.out.println("Please Select Correct Option");
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void deleteTask(Session session, UserData loggedUser) {
        try
        {
            showAllTask(session, loggedUser);
            System.out.println("Enter Task_id to remove a Task");
            int taskId = in.nextInt();
            session.createQuery("delete from TaskData where id=:taskId").setParameter("taskId", taskId).executeUpdate();

//            TaskData deleteTask = session.createQuery("from TaskData where id=:id", TaskData.class)
//                    .setParameter("id", taskId).uniqueResult();
//
//            session.delete(deleteTask);
            System.out.println("Remove Successfully!!");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void showCompletedTask(Session session, UserData loggedUser) {
        List<TaskData> allCompletedTask = session.createQuery("from TaskData where user.id=:id AND taskCompleted=:isCompleted", TaskData.class)
                .setParameter("id", loggedUser.getId())
                .setParameter("isCompleted", true).getResultList();
        for (TaskData x: allCompletedTask) {
            System.out.println(x);
        }
    }

    /**
     *
     */
    @Override
    public void showPendingTask(Session session, UserData loggedUser) {
        List<TaskData> allPendingTask = session.createQuery("from TaskData where user.id=:id AND taskCompleted=:isCompleted", TaskData.class)
                .setParameter("id", loggedUser.getId())
                .setParameter("isCompleted", false).getResultList();
        for (TaskData x: allPendingTask) {
            System.out.println(x);
        }
    }

    /**
     *
     */
    @Override
    public void searchTask(Session session, UserData loggedUser) {
        boolean searchTaskMenu = true;
        List<TaskData> taskSearch;
        while(searchTaskMenu)
        {
            System.out.println("\nSelect Option\n" +
                    "1. Search Task with Task Id\n" +
                    "2. Search Task with Task Title\n" +
                    "3. Exit");
            choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter Task Id");
                    int SearchTaskId = in.nextInt();
                    taskSearch = session.createQuery("from TaskData where user.id=:id AND id=:task_Id", TaskData.class)
                            .setParameter("id", loggedUser.getId())
                            .setParameter("task_Id", SearchTaskId).getResultList();
                    for (TaskData x : taskSearch) {
                        System.out.println(x);
                    }
                }
                case 2 -> {
                    System.out.println("Enter Task Title");
                    String SearchTaskTitle = in.next();
                    taskSearch = session.createQuery("from TaskData where user.id=:id AND TaskTitle like :taskTitle", TaskData.class)
                            .setParameter("id", loggedUser.getId())
                            .setParameter("taskTitle", "%" + SearchTaskTitle + "%").getResultList();
                    for (TaskData x : taskSearch) {
                        System.out.println(x);
                    }
                }
                case 3 -> searchTaskMenu = false;
                default -> System.out.println("Please Select Correct Option");
            }
        }
    }
}
