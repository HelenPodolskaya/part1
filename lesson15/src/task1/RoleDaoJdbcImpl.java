package task1;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoJdbcImpl implements RoleDao {
    private Connection connection;
    public static final Logger logger = LogManager.getLogger(Main.class);
    private final String INSERT_ROLE = "INSERT INTO public.role_table (id, name, description) VALUES (DEFAULT,?::\"roles_enum\",?) RETURNING id;";
    private final String UPDATE_ROLE = "UPDATE public.role_table SET name=?::\"roles_enum\", description=? WHERE id=? ;";
    private final String SELECT_ROLE_BY_NAME = "SELECT * FROM public.role_table WHERE name = ?::\"roles_enum\"";

    public RoleDaoJdbcImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Role addRole(Role role) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE);
            preparedStatement.setString(1, role.getName().toString());
            preparedStatement.setString(2, role.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
            }
            logger.info("add role {}",role);
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return role;
    }

    @Override
    public boolean updateRoleById(Role role) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);
            preparedStatement.setString(1, role.getName().toString());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.setInt(3, role.getId());
            preparedStatement.executeUpdate();
            logger.info("update role {}",role);
            return true;
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public Role getRoleByName(RolesEnum name) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_NAME);
            preparedStatement.setString(1, name.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        RolesEnum.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                logger.info("get role {}",role.getName());
                return role;
            }
        } catch (SQLException e) {
            logger.error(Level.ERROR, new Throwable(e.getMessage()));
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        return new Role(Integer.valueOf(resultSet.getString("id")),
                RolesEnum.valueOf(resultSet.getString("name")),
                resultSet.getString("description"));
    }
}
