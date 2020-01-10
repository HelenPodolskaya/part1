package task1;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRoleJdbcImpl implements UserRoleDao {
    private static Connection connection;
    public static final Logger logger = LogManager.getLogger(Main.class);
    private final String INSERT_USER_ROLE = "INSERT INTO public.user_role_table " +
            "(id,user_id,role_id) VALUES (DEFAULT,?,?);";

    public UserRoleJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addUserRole(int userID, int roleID) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, roleID);
            preparedStatement.execute();
            logger.info("add user_role userID = {}, roleID = {}", userID, roleID);
        } catch (SQLException e) {
            logger.error("userID = " + userID + " roleID = " + roleID, new Throwable(e.getMessage()), Level.ERROR);
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return true;
    }
}

