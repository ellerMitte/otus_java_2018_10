package ru.otus.frontend.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.domain.User;
import ru.otus.messageSystem.DbService;
import ru.otus.messageSystem.FrontendService;

import java.util.List;

/**
 * @author Igor on 21.03.19.
 */
@RestController
public class UserRestController {

    private final DbService dbService;
    private final FrontendService frontendService;

    public UserRestController(DbService dbService, FrontendService frontendService) {
        this.dbService = dbService;
        this.frontendService = frontendService;
    }

    @GetMapping("/users")
    public List<User> getEmployees() {
        return dbService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getEmployee(@PathVariable("id") Long id) {
        return dbService.findById(id);
    }

    @PostMapping("/users")
    public User addEmployee(@RequestBody User user) {
        frontendService.getUsers();
        return dbService.save(user);
    }

    @PutMapping("/users")
    public User updateEmployee(@RequestBody User user) {
        return dbService.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        dbService.deleteUser(id);
    }
}
