package ru.otus.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import ru.otus.app.Address;
import ru.otus.domain.User;
import ru.otus.messages.Msg;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.messages.PingMsg;
import ru.otus.service.DbService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Igor on 01.04.19.
 */
@Service
public class DbSocketStarter {

    private final String HOST = "localhost";
    private final int PORT = 5050;
    private final int PAUSE_MS = 2000;
    private final int MAX_MESSAGES_COUNT = 10;
    private final DbService dbService;

    public DbSocketStarter(DbService dbService) {
        this.dbService = dbService;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void init(String localPort, String body) throws Exception {
        SocketMsgWorker client = new DbSocketMsgWorker(HOST, PORT, Integer.valueOf(localPort));
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    System.out.println("Message received: " + msg.toString());
                    saveUser(msg);
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(dbService.getUsers());
                    msg = new PingMsg(Address.DBSERVER, Address.FRONTEND, "all", json);
                    client.send(msg);
                }
            } catch (InterruptedException | JsonProcessingException e) {
//                logger.log(Level.SEVERE, e.getMessage());
            }
        });
//        client.close();
//        executorService.shutdown();
    }

    private void saveUser(Msg msg) {
        if ("save".equals(msg.getCommand())) {
            User user = new Gson().fromJson(msg.getBody(), User.class);
            dbService.save(user);
        }
    }

}
