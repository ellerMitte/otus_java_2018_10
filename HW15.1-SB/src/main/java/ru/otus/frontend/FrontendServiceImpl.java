package ru.otus.frontend;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.frontend.domain.WSMsg;
import ru.otus.messageSystem.FrontendService;
import ru.otus.messageSystem.MessageSystemContext;
import ru.otus.messageSystem.entity.Address;
import ru.otus.messageSystem.entity.Message;
import ru.otus.messageSystem.entity.MessageSystem;
import ru.otus.messageSystem.messages.MsgDeleteUser;
import ru.otus.messageSystem.messages.MsgSaveUser;
import ru.otus.messageSystem.messages.MsgGetUsers;

import java.util.List;

/**
 * @author Igor on 25.03.19.
 */
@Service
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;
    private final SimpMessageSendingOperations sendingOperations;

    public FrontendServiceImpl(MessageSystemContext context, SimpMessageSendingOperations sendingOperations) {
        this.context = context;
        this.address = context.getFrontAddress();
        this.sendingOperations = sendingOperations;
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void saveUser(User user) {
        Message message = new MsgSaveUser(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void deleteUser(User user) {
        Message message = new MsgDeleteUser(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void getUsers() {
        Message message = new MsgGetUsers(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public void sendUsers(List<User> users) {
        sendingOperations.convertAndSend("/topic/response", new WSMsg("send", null, users));
    }
}
