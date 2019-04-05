package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.config.FrontendService;
import ru.otus.socket.FrontendSocketStarter;

/**
 * @author Igor on 03.04.19.
 */
@SpringBootApplication
public class FrontendApplication {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(FrontendApplication.class, args);
        FrontendSocketStarter starter = context.getBean(FrontendSocketStarter.class);
        FrontendService frontendService = context.getBean(FrontendService.class);
//        starter.init(args[0]);
        starter.init("45400", frontendService);
//        new FrontendSocketStarter(localport).start(Integer.valueOf(args[0]));

    }
}
