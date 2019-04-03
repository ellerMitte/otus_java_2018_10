package ru.otus.server;

import ru.otus.app.Address;
import ru.otus.app.AddressContext;
import ru.otus.app.Msg;
import ru.otus.app.MsgWorker;
import ru.otus.channel.Blocks;
import ru.otus.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class EchoSocketMsgServer implements EchoSocketMsgServerMBean {
    private static final Logger logger = Logger.getLogger(EchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final String name = "server";

    private final ExecutorService executor;
    private final List<Address> workers;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                workers.add(new Address(AddressContext.DBSERVER, worker));
            }
        }
    }

    public void acceptWorker(ServerSocket serverSocket, AddressContext address) throws IOException {
//        if (!executor.isShutdown()) {
//            Socket socket = serverSocket.accept(); //blocks
//            SocketMsgWorker worker = new SocketMsgWorker(socket);
//            worker.init();
//            workers.add(new Address(address, worker));
//        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (Address worker : workers) {
                Msg msg = worker.getWorker().poll();
                while (msg != null) {
                    System.out.println("Mirroring the message: " + msg.toString());
                    msg.getTo().send(msg);
//                    worker.send(msg);
                    msg = worker.getWorker().poll();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            logger.info("Bye.");
        }
    }
}
