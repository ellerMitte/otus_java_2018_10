import dbservice.DataSourceH2;
import dbservice.DbService;
import dbservice.DbServiceImpl;
import executor.UserExecutor;
import executor.UserExecutorImpl;
import model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Igor on 14.02.19.
 */
public class UserDemo {

    public static void main(String[] args) {

        UserExecutor demo = new UserExecutorImpl();

        demo.createTable();
        User user = new User("insertUser", 88);
        demo.saveUsers(user);
        user = demo.getUser(1L);
        System.out.println(user);
        user.setName("updateUser");
        user.setAge(14);
        demo.saveUsers(user);
        user = demo.getUser(1L);
        System.out.println(user);
    }
}
