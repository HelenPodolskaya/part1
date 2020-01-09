package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoJdbcImpl implements RoleDao {
    private static ConnectionManager connectionManager =
            ConnectionManagerJdbc.getInstance();

    @Override
    public Role addRole(Role role) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "INSERT INTO public.role_table (id, name, description) VALUES (DEFAULT,?::\"roles_enum\",?) RETURNING id;";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, role.getName().toString());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("SELECT * FROM public.role_table WHERE name =?::\"roles_enum\"");
            preparedStatement.setString(1, role.getName().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return role;
    }

    @Override
    public boolean updateRoleById(Role role) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            String statementString = "UPDATE public.role_table SET name=?::\"roles_enum\", description=? WHERE id=? ;";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, role.getName().toString());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.setInt(3, role.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public Role getRoleByName(RolesEnum name) throws MySQLException {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.role_table WHERE name = ?::\"roles_enum\"");
            preparedStatement.setString(1, name.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        RolesEnum.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        return new Role(Integer.valueOf(resultSet.getString("id")),
                RolesEnum.valueOf(resultSet.getString("name")),
                resultSet.getString("description"));
    }
}
