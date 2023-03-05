package org.todomanager.service;

public interface UserManagement {

    void login(String email, String password);

    void register(String UserName, String email, String Password);

    void logout();
}
