package org.todomanager.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.todomanager.database.HibernateConfig;
import org.todomanager.model.UserData;

import java.util.Scanner;


public class UserOpertaion implements UserManagement {
    private static SessionFactory factory = HibernateConfig.getSessionFactory();
    private Session session;

    public static void userMenu() {

        /**
         * As a user I should be able to log in and system should validate the credentials -- Done
         * As a user I should be able to add the task into a list -- Done
         * As a user I should be able to update the task in the list -- Done
         * As a user I should be able to delete the task in the list -- Done
         * As a user I should be able to search a task from a list --
         * As a user I should be able to see all my tasks -- Done
         * As a suer I should be able to see all the completed or incomplete tasks based on the requirement -- Done
         */

        System.out.println("Select Option\n" +
                "1. See All Task\n" +
                "2. Add Task\n" +
                "3. Update Task\n" +
                "4. Delete Task\n" +
                "5. Search Task\n" +
                "6. Show Completed Task\n" +
                "7. Show Incomplete Task\n" +
                "8. LogOff\n");
    }

    /**
     *
     */
    @Override
    public void login(String email, String password) {
        try {
            session = factory.openSession();
            Transaction tx = session.beginTransaction();
            UserData loggedUser = session.createQuery("from UserData where email=:mail and password=:pass", UserData.class)
                    .setParameter("mail", email)
                    .setParameter("pass", password).uniqueResult();
            if(loggedUser!=null)
            {
                boolean loggedMenu = true;
                System.err.println("\n------------------- Welcome To Your Task Manager DashBoard ---------------------\n");
                while(loggedMenu)
                {
                    TaskManagement task = new TaskOperation();
                    Scanner in = new Scanner(System.in);
                    int choice;
                    String taskTitle, taskText, assignedTo;

                    if(loggedUser.getEmail().endsWith("@admin"))
                    {
                        /**
                         * Can be Implemented further
                         */
                        //Admin Panel
                        //adminMenu();
                    }
                    else
                    {
                        //User Panel
                        userMenu();
                        choice = in.nextInt();
                        switch (choice) {
                            case 1 -> task.showAllTask(session, loggedUser);
                            case 2 -> {
                                System.out.println("Enter Task Title");
                                taskTitle = in.nextLine();
                                in.next();
                                System.out.println("Enter Task Description");
                                taskText = in.nextLine();
                                in.next();
                                task.addTask(session, taskTitle, taskText, loggedUser, false);
                                tx.commit();
                                tx = session.beginTransaction();
                            }
                            case 3 -> {
                                task.updateTask(session, loggedUser);
                                tx.commit();
                                tx = session.beginTransaction();
                            }
                            case 4 -> {
                                task.deleteTask(session, loggedUser);
                                tx.commit();
                                tx = session.beginTransaction();
                            }
                            case 5 -> task.searchTask(session, loggedUser);
                            case 6 -> task.showCompletedTask(session, loggedUser);
                            case 7 -> task.showPendingTask(session, loggedUser);
                            case 8 -> {
                                session.close();
                                loggedMenu = false;
                            }
                            default -> System.out.println("Please Select Correct Option");
                        }
                    }
                }
            }
            else
            {
                System.err.println("Email or Password Doesn't match!");
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
    public void register(String UserName, String email, String Password) {
        try
        {
            session = factory.openSession();
            Transaction tx = session.beginTransaction();
            UserData loggedUser = session.createQuery("from UserData where email=:mail OR username=:username", UserData.class)
                    .setParameter("mail", email)
                    .setParameter("username", UserName).uniqueResult();
            if(loggedUser!=null)
            {
                System.out.println("User with same Username/Email already exist!");
            }
            else
            {
                UserData newUser = new UserData(UserName, email, Password);
                session.persist(newUser);
                tx.commit();
            }
            session.close();
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
    public void logout() {
        try {
            this.session.close();
            System.out.println("Thank you for using the Tool");
        }
        catch (Exception e)
        {
            System.err.println("No User Logged in");
        }
    }
}