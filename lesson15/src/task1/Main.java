package task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        Connection connection = new DBConnection().connectDB();
        if (connection != null) {
           /* String strQueryUser = "INSERT INTO \"public\".\"user_table\" " +
                    "(id, name, \"login_id\", city, birthday, email," +
                    " description) VALUES (DEFAULT, 'Подольская Е.В.', 3, 'Казань', " +
                    "'1985-05-14', 'helen_evdokimova@ngs.ru'," +
                    " 'STC20');";
            try (PreparedStatement insert = connection.prepareStatement(strQueryUser)) {
                insert.execute();
            } catch (SQLException e) {
                System.out.println(e);
            }*/
            try {
                Statement st = connection.createStatement();
                connection.setAutoCommit(false);
                st.addBatch("INSERT INTO public.user_table (id, name, login_id, city, birthday, email,description) VALUES (DEFAULT ,'Иванов И.И.',3,'Москва','1985-12-01','ivanov@gmail.com','STC20');");
                st.addBatch("INSERT INTO public.user_table (id, name, login_id, city, birthday, email,description) VALUES (DEFAULT ,'Петров И.И.',3,'Казань','1983-07-04','petrov@gmail.com','STC20');");
                int [] count=st.executeBatch();
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}