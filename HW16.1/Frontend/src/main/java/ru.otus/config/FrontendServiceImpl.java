package ru.otus.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.domain.WSMsg;
import ru.otus.socket.FrontendSocketStarter;

import java.util.List;

/**
 * @author Igor on 25.03.19.
 */
@Service
public class FrontendServiceImpl implements FrontendService {
    private final FrontendSocketStarter msSocketService;
    private final SimpMessageSendingOperations sendingOperations;
    private final ObjectMapper mapper = new ObjectMapper();

    public FrontendServiceImpl(FrontendSocketStarter msSocketService, SimpMessageSendingOperations sendingOperations) {
        this.msSocketService = msSocketService;
        this.sendingOperations = sendingOperations;
    }

    @Override
    public void saveUser(User user) {
        msSocketService.sendMessage("SaveUser", toJson(user));
    }

    @Override
    public void deleteUser(User user) {
        msSocketService.sendMessage("DeleteUser", user.getId().toString());
    }

    @Override
    public void getUsers() {
        msSocketService.sendMessage("GetUsers", "all");
    }

    @Override
    public void sendUsers(List<User> users) {
        sendingOperations.convertAndSend("/topic/response", new WSMsg("send", null, users));
    }

    private String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("json not mapped");
            return "";
        }
    }
}
