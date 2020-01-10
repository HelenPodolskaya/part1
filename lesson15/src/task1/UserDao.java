package task1;

import java.util.List;

public interface UserDao {
    public User addUser(User user, Integer roleID) throws MySQLException;

    public List<User> getUsers(Integer login_ID, String name) throws MySQLException;

    public boolean updateUserById(User user) throws MySQLException;

    //  public boolean deleteUserById(Integer id);
}
