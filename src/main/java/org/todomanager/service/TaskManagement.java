package org.todomanager.service;

import org.hibernate.Session;
import org.todomanager.model.UserData;

/**
 * As a user I should be able to log in and system should validate the credentials -- Done
 * As a user I should be able to add the task into a list -- Done
 * As a user I should be able to update the task in the list -- Done
 * As a user I should be able to delete the task in the list -- Done
 * As a user I should be able to search a task from a list --
 * As a user I should be able to see all my tasks -- Done
 * As a suer I should be able to see all the completed or incomplete tasks based on the requirement -- Done
 */
public interface TaskManagement {

    void showAllTask(Session session, UserData user);

    void addTask(Session session, String taskTitle, String taskText, UserData user, boolean isCompleted);

    void updateTask(Session session, UserData loggedUser);

    void deleteTask(Session session, UserData loggedUser);

    void showCompletedTask(Session session, UserData loggedUser);

    void showPendingTask(Session session, UserData loggedUser);

    void searchTask(Session session, UserData loggedUser);


}
