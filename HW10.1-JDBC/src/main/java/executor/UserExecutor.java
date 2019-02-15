package executor;

import model.User;

public interface UserExecutor {
    void saveUsers(User user);
    User getUser(long id);
    void createTable();
}
