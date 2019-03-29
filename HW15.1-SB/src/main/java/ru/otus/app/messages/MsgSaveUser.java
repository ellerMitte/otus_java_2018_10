package ru.otus.app.messages;

import ru.otus.domain.User;
import ru.otus.app.context.DbService;
import ru.otus.messageSystem.Address;

public class MsgSaveUser extends MsgToDB {

    private final User user;

    public MsgSaveUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(DbService dbService) {
        dbService.save(user);
        dbService.getMS().sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
