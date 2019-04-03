package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.jpa.UserRepository;

import java.util.List;

/**
 * @author Igor on 27.03.19.
 */

@Service
public class DbServiceJPAImpl implements DbService {
    private final UserRepository userRepository;

    public DbServiceJPAImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

}
