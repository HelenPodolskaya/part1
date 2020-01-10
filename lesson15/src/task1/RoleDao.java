package task1;

public interface RoleDao {
    public Role addRole(Role role) throws MySQLException;

    public boolean updateRoleById(Role role) throws MySQLException;

    public Role getRoleByName(RolesEnum name) throws MySQLException;
}

