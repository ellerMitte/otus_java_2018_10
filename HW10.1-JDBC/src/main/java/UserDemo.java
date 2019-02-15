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
        demo.saveUsers(new User(0L,"insertUser", 88));
        System.out.println(demo.getUser(1L));

        demo.saveUsers(new User(1L,"updateUser", 14));
        System.out.println(demo.getUser(1L));
    }
}
