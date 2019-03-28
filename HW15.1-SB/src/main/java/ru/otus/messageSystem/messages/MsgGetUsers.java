package ru.otus.messageSystem.messages;

import ru.otus.domain.User;
import ru.otus.messageSystem.DbService;
import ru.otus.messageSystem.entity.Address;

import java.util.List;

public class MsgGetUsers extends MsgToDB {

    public MsgGetUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DbService dbService) {
        dbService.getMS().sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
