package ru.otus.app;

/**
 * Created by igor
 */
public abstract class Msg {
    private final MsgWorker from;
    private final MsgWorker to;
    private final String body;
    public static final String CLASS_NAME_VARIABLE = "className";

    private final String className;

    protected Msg(MsgWorker from, MsgWorker to, String body, Class<?> klass) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.className = klass.getSimpleName();
    }

    public MsgWorker getFrom() {
        return from;
    }

    public MsgWorker getTo() {
        return to;
    }
}
