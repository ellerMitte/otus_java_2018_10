package dbservice;

import model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Igor on 14.02.19.
 */
public class DbServiceDemo {

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        DbService<User> dbServiceUser = new DbServiceImpl<>(dataSource);
        dbServiceUser.save(new User(5L,"insertUser", 88));
        System.out.println(dbServiceUser.load(1L, User.class));

        dbServiceUser.save(new User(1L,"updateUser", 14));
        System.out.println(dbServiceUser.load(1L, User.class));
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(255), age integer(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
