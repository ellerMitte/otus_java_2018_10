package ru.otus.command;

import com.google.gson.Gson;
import ru.otus.app.Address;
import ru.otus.app.Commands;
import ru.otus.messages.Msg;
import ru.otus.service.DbService;

import java.util.ArrayList;
import java.util.List;

public class GetUsers implements DbCommand {

    @Override
    public List<Msg> exec(DbService dbService, Msg msg) {
        List<Msg> messages = new ArrayList<>();
        messages.add(new Msg(Address.DBSERVER, Address.FRONTEND, Commands.READ, new Gson().toJson(dbService.getUsers())));
        return messages;
    }
}
