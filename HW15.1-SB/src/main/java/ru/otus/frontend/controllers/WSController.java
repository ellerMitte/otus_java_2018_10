package ru.otus.frontend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.frontend.domain.WSMsg;
import ru.otus.messageSystem.FrontendService;

@Controller
public class WSController {

    private final FrontendService frontendService;

    public WSController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @MessageMapping("/connect")
    @SendTo("/topic/response")
    public void getConnect(WSMsg message) {
        frontendService.getUsers();
    }

    @MessageMapping("/message")
    @SendTo("/topic/response")
    public void getMessage(WSMsg message) {
        frontendService.getUsers();
    }

    @MessageMapping("/save")
    @SendTo("/topic/response")
    public void saveUser(WSMsg message) {
        frontendService.saveUser(message.getUser());
    }

    @MessageMapping("/delete")
    @SendTo("/topic/response")
    public void deleteUser(WSMsg message) {
        frontendService.deleteUser(message.getUser());
    }
}
