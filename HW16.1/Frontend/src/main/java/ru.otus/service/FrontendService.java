package ru.otus.service;

import ru.otus.domain.User;

import java.util.List;

/**
 * @author Igor on 25.03.19.
 */
public interface FrontendService {

    void saveUser(User user);

    void deleteUser(User user);

    void getUsers();

    void sendUsers(List<User> users);
}
