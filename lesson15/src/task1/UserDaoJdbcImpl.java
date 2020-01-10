package task1;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private Connection connection;
    public static final Logger logger = LogManager.getLogger(Main.class);
    private final String INSERT_USER = "INSERT INTO public.user_table (id, name, login_id, city, birthday, email, description)" +
            " VALUES (DEFAULT, ?, ?,?,?,?,?) RETURNING id;";
    private final String SELECT_USER_BY_NAME_AND_ROLEID = "SELECT * FROM public.user_table WHERE login_ID = ? and name = ?;";
    private final String UPDATE_USER = "UPDATE public.user_table SET name=?, login_id=?, city=?, birthday=?, email=?, description=? WHERE id=?;";
    public UserDaoJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User addUser(User user, Integer roleID) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, roleID);
            preparedStatement.setString(3, user.getCity());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setLogin_id(roleID);
                user.setId(resultSet.getInt("id"));
            }
            logger.info("add user {}",user);
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getUsers(Integer login_ID, String name) throws MySQLException {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME_AND_ROLEID);
            preparedStatement.setInt(1, login_ID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return userList;
    }

    @Override
    public boolean updateUserById(User user) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getLogin_id());
            preparedStatement.setString(3, user.getCity());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
            logger.info("update user {}",user);
            return true;
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(Integer.valueOf(resultSet.getString("id")),
                resultSet.getString("name"),
                Date.valueOf(resultSet.getString("birthday")),
                Integer.valueOf(resultSet.getString("login_id")),
                resultSet.getString("city"),
                resultSet.getString("email"),
                resultSet.getString("description"));
    }
}
