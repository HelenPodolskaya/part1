package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRoleJdbcImpl implements UserRoleDao {
    private static ConnectionManager connectionManager =
            ConnectionManagerJdbc.getInstance();

    @Override
    public boolean addUserRole(int userID, int roleID) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "INSERT INTO public.user_role_table " +
                    "(id,user_id,role_id) VALUES (DEFAULT,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, roleID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return true;
    }
}

