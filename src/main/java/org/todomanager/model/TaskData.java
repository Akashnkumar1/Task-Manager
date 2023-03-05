package org.todomanager.model;


import jakarta.persistence.*;

@Entity
@Table(name = "task_request")
public class TaskData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData user;

    @Column(name = "task_name")
    private String TaskTitle; //(String entered by the user)

    @Column(name = "task_desc")
    private String TaskText; //(String entered by the user)

    @Column (name = "email")
    private String assignedTo; //(String entered by the user – store email of the user)

    @Column(name = "is_completed")
    private boolean taskCompleted; //boolean(initial value will be false)

    public TaskData() {

    }

    public TaskData(String taskTitle, String taskText, String assignedTo, boolean taskCompleted) {
        TaskTitle = taskTitle;
        TaskText = taskText;
        this.assignedTo = assignedTo;
        this.taskCompleted = taskCompleted;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;
    }

    public String getTaskText() {
        return TaskText;
    }

    public void setTaskText(String taskText) {
        TaskText = taskText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "id=" + id +
                ", TaskTitle='" + TaskTitle + '\'' +
                ", TaskText='" + TaskText + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", taskCompleted=" + taskCompleted +
                '}';
    }
}
