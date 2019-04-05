package ru.otus.socket.messages;

import ru.otus.app.Address;
import ru.otus.messages.Msg;

/**
 * Created by tully.
 */
public class PingMsg {
    private final long time;

    public PingMsg(Address from, Address to, String body) {
//        super(from, to, body, PingMsg.class);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

//    @Override
//    public String toString() {
//        return "ClientPingMsg{" + "time=" + time + '}';
//    }
}
