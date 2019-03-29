package ru.otus.app.context;

import ru.otus.domain.User;
import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface DbService extends Addressee {

    List<User> getUsers();

    void save(User user);

    void deleteUser(Long id);
}