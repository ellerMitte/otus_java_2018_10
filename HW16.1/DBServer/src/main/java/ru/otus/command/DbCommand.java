package ru.otus.command;

import ru.otus.messages.Msg;
import ru.otus.service.DbService;

import java.util.List;

public interface DbCommand {
    List<Msg> exec(DbService dbService, Msg msg);
}
