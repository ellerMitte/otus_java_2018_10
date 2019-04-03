package ru.otus.socket;

import ru.otus.app.Msg;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.socket.messages.PingMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Igor on 01.04.19.
 */
public class FrontendSocketStarter {

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 2000;
    private static final int MAX_MESSAGES_COUNT = 10;

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        SocketMsgWorker client = new FrontendMsgWorker(HOST, PORT);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    System.out.println("frontend received: " + msg.toString());
                }
            } catch (InterruptedException e) {
//                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        int count = 0;
        while (count < MAX_MESSAGES_COUNT) {
//            Msg msg = new PingMsg(body, from, to);
//            client.send(msg);
//            System.out.println("frontend sent: " + msg.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }
        client.close();
        executorService.shutdown();
    }
}
