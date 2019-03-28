package ru.otus.messageSystem.messages;

import ru.otus.domain.User;
import ru.otus.messageSystem.FrontendService;
import ru.otus.messageSystem.entity.Address;

import java.util.List;

public class MsgGetUsersAnswer extends MsgToFrontend {
    private final List<User> users;

    public MsgGetUsersAnswer(Address from, Address to, List<User> users) {
        super(from, to);
        this.users = users;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendUsers(users);
    }
}
