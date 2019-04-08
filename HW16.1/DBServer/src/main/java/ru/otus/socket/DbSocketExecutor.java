package ru.otus.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.channel.SocketMsgWorker;
import ru.otus.command.DbCommand;
import ru.otus.messages.Msg;
import ru.otus.service.DbService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Igor on 01.04.19.
 */
@Slf4j
@Service
public class DbSocketExecutor {

    private final String HOST = "localhost";
    private final int PORT = 5050;
    private final DbService dbService;
    private final String PATH_TO_COMMAND = "ru.otus.command.";

    public DbSocketExecutor(DbService dbService) {
        this.dbService = dbService;
    }

    public void init(String localPort) throws Exception {
        SocketMsgWorker client = new DbSocketMsgWorker(HOST, PORT, Integer.valueOf(localPort));
        client.init();

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    log.debug("db (" + localPort + ") received: " + msg.toString());
                    List<Msg> messages = execute(msg);
                    messages.forEach(client::send);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        });
        threadExecutor.shutdown();
    }

    private List<Msg> execute(Msg msg) {
        try {
            DbCommand dbCommand = (DbCommand) Class.forName(PATH_TO_COMMAND + msg.getCommand().getCommandClass()).newInstance();
            return dbCommand.exec(dbService, msg);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
