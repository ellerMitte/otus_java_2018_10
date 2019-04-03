package ru.otus;

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

        new DbSocketStarter().start();

    }
}
