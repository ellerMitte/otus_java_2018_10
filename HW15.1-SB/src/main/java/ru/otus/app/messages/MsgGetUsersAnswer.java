package ru.otus.app.messages;

import ru.otus.domain.User;
import ru.otus.app.context.FrontendService;
import ru.otus.messageSystem.Address;

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
