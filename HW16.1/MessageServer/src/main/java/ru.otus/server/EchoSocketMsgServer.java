package ru.otus.server;

import ru.otus.app.*;
import ru.otus.channel.Blocks;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.messages.Msg;

import java.net.ServerSocket;
import java.net.Socket;
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

//    private final String name = "server";

    private final ExecutorService executor;
    private final AddressService workersService;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workersService = new AddressServiceImpl();
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
                        System.out.println("Mirroring the message: " + msg.toString());
//                        workersService.getWorker(msg.getTo()).send(msg);
                        workersService.sendMessage(msg);
                        msg = worker.poll();
                    }
                }
                else {
                    workersService.deleteWorker(worker);
                }
            }
//            try {
//                Thread.sleep(MIRROR_DELAY_MS);
//            } catch (InterruptedException e) {
//                logger.log(Level.SEVERE, e.toString());
//            }
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
