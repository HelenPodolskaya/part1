package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Connection connection = new DBConnection().connectDB();
        if (connection != null) {
            String strQueryUser = "INSERT INTO \"test_database\".\"USER_TABLE\" " +
                    "(id, name, \"login_ID\", city, birthday, email," +
                    " description) VALUES (DEFAULT, 'Подольская Е.В.', 3, 'Казань', " +
                    "'1985-05-14', 'helen_evdokimova@ngs.ru'," +
                    " 'STC20');";
            try (PreparedStatement insert = connection.prepareStatement(strQueryUser)) {
                insert.execute();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}