package ru.otus.messages;

import ru.otus.app.Address;

/**
 * Created by igor
 */
public abstract class Msg {
    private final Address from;
    private final Address to;
    private final String command;
    private final String body;
//    public static final String CLASS_NAME_VARIABLE = "className";

//    private final String className;

    protected Msg(Address from, Address to,String command, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.command = command;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public String getCommand() {
        return command;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "from=" + from +
                ", to=" + to +
                ", body='" + body + '\'' +
                ", command='" + command + '\'' +
                '}';
    }
}
