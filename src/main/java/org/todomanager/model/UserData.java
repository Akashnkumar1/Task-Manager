package org.todomanager.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "credentials")
    private String password;

    @OneToMany(mappedBy = "user")
    //@JoinColumn(name = "task_id")
    private Set<TaskData> task;

    public UserData() {

    }

    public UserData(String username, String email, String password, Set<TaskData> task) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.task = task;
    }

    public UserData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<TaskData> getTask() {
        return task;
    }

    public void setTask(Set<TaskData> task) {
        this.task = task;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
