package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryUserDao implements UserDao {

    private final Map<String, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put("John", new User("John", "qwerty"));
        users.put("Freddy", new User("Freddy", "12345"));
        users.put("Petro", new User("Petro", "pass"));
    }

    @Override
    public List<User> getUsers() {
        return users.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        return users.put(user.getName(), user);
    }

    @Override
    public User findByName(String name) {
        return users.get(name);
    }

    @Override
    public void deleteUserByName(String name) {
        users.remove(name);
    }
}
