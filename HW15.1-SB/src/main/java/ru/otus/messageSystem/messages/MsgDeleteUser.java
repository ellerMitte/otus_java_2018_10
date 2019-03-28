package ru.otus.messageSystem.messages;

import ru.otus.domain.User;
import ru.otus.messageSystem.DbService;
import ru.otus.messageSystem.entity.Address;

public class MsgDeleteUser extends MsgToDB {

    private final User user;

    public MsgDeleteUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(DbService dbService) {
        dbService.deleteUser(user.getId());
        dbService.getMS().sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
