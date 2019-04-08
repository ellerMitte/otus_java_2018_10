package ru.otus.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.app.Address;
import ru.otus.app.Commands;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.service.FrontendService;
import ru.otus.domain.User;
import ru.otus.messages.Msg;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Igor on 01.04.19.
 */

@Slf4j
@Service
public class FrontendSocketExecutor {

    private final String HOST = "localhost";
    private final int PORT = 5050;
    private SocketMsgWorker client;
    private FrontendService frontendService;

    public void init(String localPort, FrontendService frontendService) throws Exception {
        this.frontendService = frontendService;
        client = new FrontendMsgWorker(HOST, PORT, Integer.valueOf(localPort));
        client.init();

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    getUsers(msg);
                    log.debug("frontend (" + localPort + ") received: " + msg.toString());
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        });
        threadExecutor.shutdown();
    }

    private void getUsers(Msg msg) {
        if (msg.getCommand() == Commands.READ) {
            List<User> users = new Gson().fromJson(msg.getBody(), new TypeToken<List<User>>() {
            }.getType());
            frontendService.sendUsers(users);
        }
    }

    public void sendMessage(Commands command, String body) {
        Msg msg = new Msg(Address.FRONTEND, Address.DBSERVER, command, body);
        client.send(msg);
    }
}
