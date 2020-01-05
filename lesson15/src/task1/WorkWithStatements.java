package task1;

import java.sql.*;

public class WorkWithStatements {
    public static void ExecParamStatement(String statementString, Connection connection) throws MySQLException {
        if (connection != null) {
            try (PreparedStatement insert = connection.prepareStatement(statementString)) {
                insert.execute();
            } catch (SQLException e) {
                throw new MySQLException(e.getErrorCode(), e.getMessage());
            }
        }
    }

    public static ResultSet ExecQueryStatement(String statementString, Connection connection) throws MySQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    public static ResultSet ExecQueryParamStatement(PreparedStatement preparedStatement, Connection connection) throws MySQLException {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }

    public static void ExecBatchStatement(String[] statementStrings, Connection connection) throws MySQLException {
        try {
            Statement st = connection.createStatement();
            connection.setAutoCommit(false);
            for (int i = 0; i < statementStrings.length; i++)
                st.addBatch(statementStrings[i]);
            int[] count = st.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new MySQLException(e.getErrorCode(), e.getMessage());
        }
    }
}
