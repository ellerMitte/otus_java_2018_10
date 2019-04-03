package ru.otus.socket;

import ru.otus.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Igor on 01.04.19.
 */
public class FrontendMsgWorker extends SocketMsgWorker {
    private final Socket socket;
    private final String name = "db";

    FrontendMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private FrontendMsgWorker(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    public void close() {
        try {
            super.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
