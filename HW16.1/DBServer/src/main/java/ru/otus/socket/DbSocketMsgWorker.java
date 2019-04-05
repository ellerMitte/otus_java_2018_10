package ru.otus.socket;

import ru.otus.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Igor on 01.04.19.
 */
public class DbSocketMsgWorker extends SocketMsgWorker {
    private final Socket socket;

    DbSocketMsgWorker(String host, int port, int localport) throws IOException {
        this(new Socket(host, port, null, localport));
    }

    private DbSocketMsgWorker(Socket socket) {
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
