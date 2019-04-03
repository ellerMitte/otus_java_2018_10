package ru.otus.socket.messages;

import ru.otus.app.Msg;
import ru.otus.app.MsgWorker;

/**
 * @author Igor on 03.04.19.
 */
public class MsgSaveUser extends Msg {
    private final String COMMAND = "save";

    public MsgSaveUser(MsgWorker from, MsgWorker to, String body) {
        super(from, to, body, MsgSaveUser.class);
    }

    @Override
    public String toString() {
        return "MsgSaveUser{" +
                "COMMAND='" + COMMAND + '\'' +
                '}';
    }
}
