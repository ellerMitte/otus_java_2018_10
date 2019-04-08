package ru.otus.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.service.FrontendService;
import ru.otus.domain.WSMsg;

@Controller
public class WSController {

    private final FrontendService frontendService;

    public WSController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @MessageMapping({"/connect", "/message"})
    @SendTo("/topic/response")
    public void getUsers() {
        frontendService.getUsers();
    }

    @MessageMapping("/save")
    @SendTo("/topic/response")
    public void saveUser(WSMsg message) {
        if (message.getUser() != null) {
            frontendService.saveUser(message.getUser());
        }
    }

    @MessageMapping("/delete")
    @SendTo("/topic/response")
    public void deleteUser(WSMsg message) {
        if (message.getUser() != null) {
            frontendService.deleteUser(message.getUser());
        }
    }
}
