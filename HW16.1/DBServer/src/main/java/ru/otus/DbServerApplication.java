package ru.otus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.domain.User;
import ru.otus.service.DbService;
import ru.otus.socket.DbSocketStarter;

@EnableJpaRepositories
@SpringBootApplication
public class DbServerApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(DbServerApplication.class, args);

        DbService dbService = context.getBean(DbService.class);

        dbService.save(new User(null, "ivan", "ivanych"));
        dbService.save(new User(null, "pettr", "ivanych"));
        dbService.save(new User(null, "stepan", "ivanych"));
        System.out.println(dbService.getUsers());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dbService.getUsers());

        DbSocketStarter starter = context.getBean(DbSocketStarter.class);
        starter.init("60100", json);
//        starter.init(args[0], json);

//        new DbSocketStarter(dbService).start(Integer.valueOf(args[0]), json, dbService);

    }
}
