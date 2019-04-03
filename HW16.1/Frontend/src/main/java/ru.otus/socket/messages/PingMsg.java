package ru.otus.socket.messages;

import ru.otus.app.Msg;
import ru.otus.app.MsgWorker;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {
    private final long time;

    public PingMsg(MsgWorker from, MsgWorker to, String body) {
        super(from, to, body, PingMsg.class);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "FrontendPingMsg{" + "time=" + time + '}';
    }
}
