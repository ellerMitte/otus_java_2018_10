package ru.otus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.app.Commands;
import ru.otus.domain.User;
import ru.otus.domain.WSMsg;
import ru.otus.socket.FrontendSocketExecutor;

import java.util.List;

/**
 * @author Igor on 25.03.19.
 */
@Service
public class FrontendServiceImpl implements FrontendService {
    private final FrontendSocketExecutor msSocketService;
    private final SimpMessageSendingOperations sendingOperations;

    public FrontendServiceImpl(FrontendSocketExecutor msSocketService, SimpMessageSendingOperations sendingOperations) {
        this.msSocketService = msSocketService;
        this.sendingOperations = sendingOperations;
    }

    @Override
    public void saveUser(User user) {
        msSocketService.sendMessage(Commands.SAVE, new Gson().toJson(user));
    }

    @Override
    public void deleteUser(User user) {
        msSocketService.sendMessage(Commands.DELETE, new Gson().toJson(user));
    }

    @Override
    public void getUsers() {
        msSocketService.sendMessage(Commands.READ, "");
    }

    @Override
    public void sendUsers(List<User> users) {
        sendingOperations.convertAndSend("/topic/response", new WSMsg("send", null, users));
    }
}
