package task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.MapMessage;

import java.sql.*;
import java.util.ArrayList;


/**
 * 1)    Спроектировать базу
 * -      Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
 * -      Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
 * -      Таблица USER_ROLE содержит поля id, user_id, role_id
 * Типы полей на ваше усмотрению, возможно использование VARCHAR(255)
 * 2)      Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 * a)      Используя параметризированный запрос
 * b)      Используя batch процесс
 * 3)      Сделать параметризированную выборку по login_ID и name одновременно
 * 4)      Перевести connection в ручное управление транзакциями
 * a)      Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 * между sql операциями установить логическую точку сохранения(SAVEPOINT)
 * б)   Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 * между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на
 * последней операции, что бы транзакция откатилась к логической точке SAVEPOINT A
 */
public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConnectionManager connectionManager = ConnectionManagerJdbc.getInstance();
            Connection connection = connectionManager.getConnection();
            addRole(connection, new Role(RolesEnum.Clients, "Clients"));
            addRole(connection, new Role(RolesEnum.Administration, "Administration"));
            Role role = getRole(connection, RolesEnum.Clients);
            User user = addUser(connection, new User("Горбунов П.В.", Date.valueOf("1987-10-01"), "Казань", "gorbunov@gmail.com", "STC20"), role.getId());
            addUserRole(connection, user.getId(), role.getId());
            user = addUser(connection, new User("Мангутова А.В", Date.valueOf("1979-07-07"), "Казань", "mangutova@gmail.com", "STC20"), role.getId());
            addUserRole(connection, user.getId(), role.getId());
            user = addUser(connection, new User("Подольская Е.В.", Date.valueOf("1979-07-07"), "Казань", "e.podolskaya.stc@innopolis.university", "STC20"), role.getId());
            user.setBirthday(Date.valueOf("1985-05-14"));
            updateUser(connection, user);
            addUserRole(connection, user.getId(), role.getId());
        } catch (MySQLException ex) {
            System.out.println(ex.getMessage());
            logger.throwing(Level.INFO, new Throwable(ex.getMessage()));
        }
    }

    private static void addRole(Connection connection, Role role) throws MySQLException {
        RoleDao roleDao = new RoleDaoJdbcImpl(connection);
        roleDao.addRole(role);
    }

    private static Role getRole(Connection connection, RolesEnum rolesEnum) throws MySQLException {
        RoleDao roleDao = new RoleDaoJdbcImpl(connection);
        return roleDao.getRoleByName(rolesEnum);
    }

    private static User addUser(Connection connection, User user, Integer roleID) throws MySQLException {
        UserDao userDao = new UserDaoJdbcImpl(connection);
        return userDao.addUser(user, roleID);
    }

    private static void updateUser(Connection connection, User user) throws MySQLException {
        UserDao userDao = new UserDaoJdbcImpl(connection);
        userDao.updateUserById(user);
    }

    private static void addUserRole(Connection connection, Integer userID, Integer roleID) throws MySQLException {
        UserRoleDao userRoleDao = new UserRoleJdbcImpl(connection);
        userRoleDao.addUserRole(userID, roleID);
    }
}
