package ru.otus.messageSystem;

import ru.otus.domain.User;
import ru.otus.messageSystem.entity.Addressee;

import java.util.List;

/**
 * @author Igor on 25.03.19.
 */
public interface FrontendService extends Addressee {

    void saveUser(User user);

    void deleteUser(User user);

    void getUsers();

    void sendUsers(List<User> users);
}
