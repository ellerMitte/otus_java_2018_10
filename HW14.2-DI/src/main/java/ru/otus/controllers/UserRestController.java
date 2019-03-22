package ru.otus.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.DbService.UserDao;
import ru.otus.domain.User;

import java.util.List;

/**
 * @author Igor on 21.03.19.
 */
@RestController
public class UserRestController {

    private final UserDao userDao;

    public UserRestController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public List<User> getEmployees() {
        return userDao.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getEmployee(@PathVariable("id") Long id) {
        return userDao.findById(id);
    }

    @PostMapping("/users")
    public User addEmployee(@RequestBody User user) {
        return userDao.save(user);
    }

    @PutMapping("/users")
    public User updateEmployee(@RequestBody User user) {
        return userDao.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        userDao.deleteUser(id);
    }
}
