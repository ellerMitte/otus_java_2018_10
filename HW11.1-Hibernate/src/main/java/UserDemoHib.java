import DbService.UserExecutor;
import DbService.UserExecutorImpl;
import model.Address;
import model.Phone;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 14.02.19.
 */
public class UserDemoHib {

    public static void main(String[] args) {

        UserExecutor demo = new UserExecutorImpl();

        User user = new User("insertUser", 88);
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone("3222233", user));
        phones.add(new Phone("2741001", user));
        phones.add(new Phone("5888855", user));
        user.setPhones(phones);
        Address address = new Address(user, "tverskaja");
        user.setAddress(address);
        demo.saveUsers(user);

        user = demo.getUser(1L);
        System.out.println(user);
        System.out.println();

        user.setName("updateUser");
        user.setAge(14);
        address.setStreet("red square");
        user.setAddress(address);
        user.getPhones().get(1).setNumber("555");
        demo.saveUsers(user);

        user = demo.getUser(1L);
        System.out.println(user);
    }
}
