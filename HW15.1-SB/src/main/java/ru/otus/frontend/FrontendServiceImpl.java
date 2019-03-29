package ru.otus.frontend;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.frontend.domain.WSMsg;
import ru.otus.app.context.FrontendService;
import ru.otus.app.context.MessageSystemContext;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Message;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.app.messages.MsgDeleteUser;
import ru.otus.app.messages.MsgSaveUser;
import ru.otus.app.messages.MsgGetUsers;

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
