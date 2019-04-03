package ru.otus.socket.messages;

import ru.otus.app.Msg;

/**
 * @author Igor on 03.04.19.
 */
public class MsgSaveUser extends Msg {
    private final String COMMAND = "save";

    public MsgSaveUser() {
        super(MsgSaveUser.class);
    }

    @Override
    public String toString() {
        return "MsgSaveUser{" +
                "COMMAND='" + COMMAND + '\'' +
                '}';
    }
}
