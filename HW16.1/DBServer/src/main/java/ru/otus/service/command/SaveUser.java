package ru.otus.service.command;

import com.google.gson.Gson;
import ru.otus.domain.User;
import ru.otus.service.DbService;

public class SaveUser implements DbCommand {

    @Override
    public void exec(DbService dbService, String body) {
        User user = new Gson().fromJson(body, User.class);
        dbService.save(user);
    }
}
