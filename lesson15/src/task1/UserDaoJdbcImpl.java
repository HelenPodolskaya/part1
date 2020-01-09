package task1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private static ConnectionManager connectionManager =
            ConnectionManagerJdbc.getInstance();

    @Override
    public User addUser(User user, Integer roleID) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "INSERT INTO public.user_table (id, name, login_id, city, birthday, email, description)" +
                    " VALUES (DEFAULT, ?, ?,?,?,?,?) RETURNING id;";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, roleID);
            preparedStatement.setString(3, user.getCity());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getUsers(Integer login_ID, String name) throws MySQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "SELECT * FROM public.user_table WHERE login_ID = ? and name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setInt(1, login_ID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return userList;
    }

    @Override
    public boolean updateUserById(User user) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "UPDATE public.user_table SET name=?, login_id=?, city=?, birthday=?, email=?, description=? WHERE id=?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getLogin_id());
            preparedStatement.setString(3, user.getCity());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
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
