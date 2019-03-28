package ru.otus.messageSystem;

import ru.otus.domain.User;
import ru.otus.messageSystem.entity.Addressee;

import java.util.List;

public interface DbService extends Addressee {

    List<User> getUsers();

    User save(User user);

    List<User> findByName(String name);

    User findById(Long id);

    void deleteUser(Long id);
}