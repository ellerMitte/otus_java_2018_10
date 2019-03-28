package ru.otus.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.messageSystem.FrontendService;

@Controller
public class MainController {
    private final FrontendService frontendService;

    public MainController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @GetMapping("/msg")
    public String getUsers() {
//        frontendService.saveUser("user");
        return "message.html";
    }
}
