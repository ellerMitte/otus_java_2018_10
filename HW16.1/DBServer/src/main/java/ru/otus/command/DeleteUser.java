package ru.otus.command;

import com.google.gson.Gson;
import ru.otus.app.Address;
import ru.otus.app.Commands;
import ru.otus.domain.User;
import ru.otus.messages.Msg;
import ru.otus.service.DbService;

import java.util.ArrayList;
import java.util.List;

public class DeleteUser implements DbCommand {

    @Override
    public List<Msg> exec(DbService dbService, Msg msg) {
        User user = new Gson().fromJson(msg.getBody(), User.class);
        dbService.deleteUser(user);
        List<Msg> messages = new ArrayList<>();
        if (msg.getTo() != msg.getFrom()) {
            messages.add(new Msg(Address.DBSERVER, Address.FRONTEND, Commands.READ, new Gson().toJson(dbService.getUsers())));
            messages.add(new Msg(Address.DBSERVER, Address.DBSERVER, Commands.DELETE, new Gson().toJson(user)));
        }
        return messages;
    }
}
