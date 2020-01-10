package task1;

public interface UserRoleDao {
    public boolean addUserRole(int userID, int roleID) throws MySQLException;
}