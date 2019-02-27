package model;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User save(User user);

    User findByName(String name);

    void deleteUserByName(String name);

}