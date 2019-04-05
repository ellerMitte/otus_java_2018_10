package ru.otus.messages;

import ru.otus.app.Address;

/**
 * Created by igor.
 */
public class PingMsg extends Msg {
    private final long time;

    public PingMsg(Address from, Address to,String command, String body) {
        super(from, to, command, body);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

//    @Override
//    public String toString() {
//        return "ServerPingMsg{" + "time=" + time + '}';
//    }

}
