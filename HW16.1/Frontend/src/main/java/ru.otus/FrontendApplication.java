package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.socket.FrontendSocketStarter;

/**
 * @author Igor on 03.04.19.
 */
@SpringBootApplication
public class FrontendApplication {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(FrontendApplication.class, args);

        new FrontendSocketStarter().start();

    }
}
