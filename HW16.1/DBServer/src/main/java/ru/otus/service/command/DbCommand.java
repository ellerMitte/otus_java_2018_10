package ru.otus.service.command;

import ru.otus.messages.Msg;
import ru.otus.service.DbService;

public interface DbCommand {
    void exec(DbService dbService, String body);
}
