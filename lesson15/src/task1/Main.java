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
    private static ArrayList<User> usersList = new ArrayList<>();

    public static void main(String[] args) {
        Connection connection = new DBConnection().connectDB();
        if (connection != null) {
            try {
                WorkWithStatements.ExecParamStatement("INSERT INTO public.user_table " +
                        "(id, name, login_id, city, birthday, email," +
                        " description) VALUES (DEFAULT, 'Горбунов П.В.', 3, 'Казань', " +
                        "'1987-10-01', 'gorbunov@gmail.com'," +
                        " 'STC20');", connection);
                String[] paramStrings = new String[]{"INSERT INTO public.user_table (id, name, login_id, city, birthday, email,description) VALUES (DEFAULT ,'Иванова И.Е.',3,'Москва','1985-11-11','ivanova@gmail.com','STC20');",
                        "INSERT INTO public.user_table (id, name, login_id, city, birthday, email,description) VALUES (DEFAULT ,'Сидоров С.И.',3,'Москва','1990-02-01','sidorov@gmail.com','STC20');"};
                WorkWithStatements.ExecBatchStatement(paramStrings, connection);
                ResultSet resultSet = WorkWithStatements.ExecQueryStatement("SELECT * FROM public.user_table WHERE login_ID = 3 and name = 'Иванов И.И.'", connection);
                while (resultSet.next()) {
                    User user = getUser(resultSet);
                    usersList.add(user);
                    System.out.print(user.toString());
                }
                User user = new User("Мангутова А.В", Date.valueOf("1979-07-07"), "Казань", "mangutova@gmail.com", "STC20");
                Role role = new Role(RolesEnum.Administration, "admin");
                savePointMethod(connection, role, user);
            } catch (MySQLException e) {
                System.out.println(e);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    private static void savePointMethod(Connection connection, Role newRole, User newUser) throws SQLException {
        connection.setAutoCommit(false);
        Savepoint savepoint = connection.setSavepoint("savepoint");
        try {
            newRole = insertRole(connection, newRole);
            newUser = insertUser(connection, newUser, newRole.getId());
            if (newRole.getId() != null && newUser.getId() != null) {
                insertUserRole(connection, newUser.getId(), newRole.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepoint);
            System.out.println("откатились к savepoint" + e);
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    private static User insertUser(Connection connection, User newUser, Integer roleID) throws MySQLException {
        String statementString = "INSERT INTO public.user_table (id, name, login_id, city, birthday, email, description) VALUES (DEFAULT, ?, ?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, newUser.getName());
            preparedStatement.setInt(2, roleID);
            preparedStatement.setString(3, newUser.getCity());
            preparedStatement.setDate(4, newUser.getBirthday());
            preparedStatement.setString(5, newUser.getEmail());
            preparedStatement.setString(6, newUser.getDescription());
            WorkWithStatements.ExecQueryParamStatement(preparedStatement, connection);
            ResultSet resultSet = WorkWithStatements.ExecQueryStatement("SELECT * FROM public.user_table WHERE name = 'Мангутова А.В.'", connection);
            while (resultSet.next()) {
                newUser = getUser(resultSet);
            }
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return newUser;
    }

    private static Role insertRole(Connection connection, Role newRole) throws MySQLException {
        try {
            String statementString = "INSERT INTO public.role_table (id, name, description) VALUES (DEFAULT,?::\"roles_enum\",?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, newRole.getName().toString());
            preparedStatement.setString(2, newRole.getDescription());
            WorkWithStatements.ExecQueryParamStatement(preparedStatement, connection);
            ResultSet resultSet = WorkWithStatements.ExecQueryStatement("SELECT * FROM public.role_table WHERE name = " + newRole.getName() + ";", connection);
            while (resultSet.next()) {
                newRole = getRole(resultSet);
            }
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
        return newRole;
    }

    private static void insertUserRole(Connection connection, int userID, int roleID) throws MySQLException {
        try {
            String statementString = "INSERT INTO public.user_role_table " +
                    "(id,user_id,role_id) VALUES (DEFAULT,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, roleID);
            WorkWithStatements.ExecQueryParamStatement(preparedStatement, connection);
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    private static User getUser(ResultSet resultSet) throws SQLException {
        return new User(Integer.valueOf(resultSet.getString("id")),
                resultSet.getString("name"),
                Date.valueOf(resultSet.getString("birthday")),
                Integer.valueOf(resultSet.getString("login_id")),
                resultSet.getString("city"),
                resultSet.getString("email"),
                resultSet.getString("description"));
    }

    private static Role getRole(ResultSet resultSet) throws SQLException {
        return new Role(Integer.valueOf(resultSet.getString("id")),
                RolesEnum.valueOf(resultSet.getString("name")),
                resultSet.getString("description"));
    }
}