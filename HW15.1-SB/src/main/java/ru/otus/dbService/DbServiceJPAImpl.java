package ru.otus.dbService;

import org.springframework.stereotype.Service;
import ru.otus.app.context.DbService;
import ru.otus.app.context.MessageSystemContext;
import ru.otus.dbService.jpa.UserRepository;
import ru.otus.domain.User;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

import java.util.List;

/**
 * @author Igor on 27.03.19.
 */
@Service
public class DbServiceJPAImpl implements DbService {
    private final UserRepository userRepository;
    private final Address address;
    private final MessageSystemContext context;

    public DbServiceJPAImpl(UserRepository userRepository, MessageSystemContext context) {
        this.userRepository = userRepository;
        this.context = context;
        this.address = context.getDbAddress();
        context.getMessageSystem().addAddressee(this);
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
