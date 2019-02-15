package executor;

import dbservice.DataSourceH2;
import dbservice.DbService;
import dbservice.DbServiceImpl;
import model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Igor on 15.02.19.
 */
public class UserExecutorImpl implements UserExecutor {

    private final DbService<User> dbService;
    private final DataSource dataSource;

    public UserExecutorImpl() {
        dataSource = new DataSourceH2();
        dbService = new DbServiceImpl<>(dataSource);
    }

    @Override
    public void saveUsers(User user) {
        dbService.save(user);
    }

    @Override
    public User getUser(long id) {
        return dbService.load(id, User.class);
    }

    @Override
    public void createTable() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(255), age integer(3))")) {
            pst.executeUpdate();
            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println("table not created!");
            e.printStackTrace();
        }
    }
}
