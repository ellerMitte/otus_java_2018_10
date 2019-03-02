package DbService;

import model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User save(User user);

    User findByName(String name);
}