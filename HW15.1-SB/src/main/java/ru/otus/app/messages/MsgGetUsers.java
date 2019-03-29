package ru.otus.app.messages;

import ru.otus.app.context.DbService;
import ru.otus.messageSystem.Address;

public class MsgGetUsers extends MsgToDB {

    public MsgGetUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DbService dbService) {
        dbService.getMS().sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
