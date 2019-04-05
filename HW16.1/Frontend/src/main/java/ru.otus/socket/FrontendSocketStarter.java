package ru.otus.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import ru.otus.app.Address;
import ru.otus.config.FrontendService;
import ru.otus.domain.User;
import ru.otus.messages.Msg;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.messages.PingMsg;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Igor on 01.04.19.
 */

@Service
public class FrontendSocketStarter {

    private final String HOST = "localhost";
    private final int PORT = 5050;
    private final int PAUSE_MS = 2000;
    private final int MAX_MESSAGES_COUNT = 10;
    private SocketMsgWorker client;
    private FrontendService frontendService;

    @SuppressWarnings("InfiniteLoopStatement")
    public void init(String localPort, FrontendService frontendService) throws Exception {
        this.frontendService = frontendService;
        client = new FrontendMsgWorker(HOST, PORT, Integer.valueOf(localPort));
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    getUsers(msg);
                    System.out.println("frontend received: " + msg.toString());
                }
            } catch (InterruptedException e) {
//                logger.log(Level.SEVERE, e.getMessage());
            }
        });

//        Thread.sleep(30_000);
//        int count = 0;
//        while (count < MAX_MESSAGES_COUNT) {
//            Msg msg = new PingMsg(Address.FRONTEND, Address.DBSERVER, "message from frontend" + count);
//            client.send(msg);
//            System.out.println("frontend sent: " + msg.toString());
//            Thread.sleep(PAUSE_MS);
//            count++;
//        }
//        client.close();
//        executorService.shutdown();
    }

    private void getUsers(Msg msg) {
        if ("all".equals(msg.getCommand())) {
            List<User> users = new Gson().fromJson(msg.getBody(), new TypeToken<List<User>>() {}.getType());
            frontendService.sendUsers(users);
        }
    }

    public void sendMessage(String command, String body) {
        Msg msg = new PingMsg(Address.FRONTEND, Address.DBSERVER, command, body);
        client.send(msg);
    }
}
