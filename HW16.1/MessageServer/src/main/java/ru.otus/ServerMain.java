package ru.otus;

import ru.otus.app.Address;
import ru.otus.runner.ProcessRunnerImpl;
import ru.otus.server.SocketMsgServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by igor.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/DBServer-2018-10.jar";
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/Frontend-2018-10.jar";
    private static final String FRONTEND_SERVER_PORT_COMMAND = " --server.port=";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        startClient(executorService, DBSERVER_START_COMMAND + " " + Address.DBSERVER.getMinPort());
        startClient(executorService, DBSERVER_START_COMMAND + " " + (Address.DBSERVER.getMinPort() + 1));
        startClient(executorService, FRONTEND_START_COMMAND + " "
                + Address.FRONTEND.getMinPort() + FRONTEND_SERVER_PORT_COMMAND + "8080");
        startClient(executorService, FRONTEND_START_COMMAND + " "
                + (Address.FRONTEND.getMinPort() + 1) + FRONTEND_SERVER_PORT_COMMAND + "8090");

        SocketMsgServer server = new SocketMsgServer();

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }

}
