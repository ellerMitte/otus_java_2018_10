package ru.otus.server;

import ru.otus.app.Address;
import ru.otus.app.AddressService;
import ru.otus.app.AddressServiceImpl;
import ru.otus.channel.MsgWorker;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.messages.Msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by igor.
 */
public class SocketMsgServer {
    private static final Logger logger = Logger.getLogger(SocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;

    private final ExecutorService executor;
    private final AddressService workersService;

    public SocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workersService = new AddressServiceImpl();
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                workersService.addWorker(Address.getAddress(socket.getPort()), worker);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgWorker worker : workersService.getWorkers()) {
                if (worker.isConnected()) {
                    Msg msg = worker.poll();
                    while (msg != null) {
                        workersService.sendMessage(worker, msg);
                        msg = worker.poll();
                    }
                }
                else {
                    workersService.deleteWorker(worker);
                }
            }
        }
    }
}
