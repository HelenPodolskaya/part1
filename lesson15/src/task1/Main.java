package task1;

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

    public static void main(String[] args) {
        try {
            User user = new User("Горбунов П.В.", Date.valueOf("1987-10-01"), "Казань", "gorbunov@gmail.com", "STC20");
            RoleDao roleDao = new RoleDaoJdbcImpl();
            roleDao.addRole(new Role(RolesEnum.Clients, "Clients"));
            Role role = roleDao.getRoleByName(RolesEnum.Clients);
            UserDao userDao = new UserDaoJdbcImpl();
            userDao.addUser(user, role.getId());
            user = new User("Мангутова А.В", Date.valueOf("1979-07-07"), "Казань", "mangutova@gmail.com", "STC20");
            userDao.addUser(user, role.getId());
        } catch (MySQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
