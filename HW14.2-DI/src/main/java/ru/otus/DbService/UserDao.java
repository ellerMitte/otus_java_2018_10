package ru.otus.DbService;

import ru.otus.domain.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User save(User user);

    User findByName(String name);

    User findById(Long id);

    void deleteUser(Long id);
}