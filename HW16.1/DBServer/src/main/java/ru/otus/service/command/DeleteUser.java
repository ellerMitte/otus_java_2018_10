package ru.otus.service.command;

import ru.otus.service.DbService;

public class DeleteUser implements DbCommand {

    @Override
    public void exec(DbService dbService, String body) {
        dbService.deleteUser(Long.valueOf(body));
    }
}
