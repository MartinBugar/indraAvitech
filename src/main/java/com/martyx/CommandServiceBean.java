package com.martyx;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandServiceBean implements CommandService {

    private static final String Q_GET_ALL_USERS = "SELECT * FROM susers";
    private static final String Q_DELETE_ALL = "DELETE FROM susers";
    private static final String Q_INSERT_USER = "INSERT INTO susers (user_id, user_guid, user_name) VALUES (";

    public CommandServiceBean() {
        super();
    }

    private static Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 embedded database.");
        return connection;
    }

    @Override
    public List<UserDefinition> printAll() throws SQLException {
        List<UserDefinition> users = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Q_GET_ALL_USERS);

        int count = 0;

        while (resultSet.next()) {
            count++;
            int userId = resultSet.getInt("user_id");
            String user_name = resultSet.getString("user_name");
            String userGuid = resultSet.getString("user_guid");
            System.out.println("UserEntity #" + count + ": " + userId + ", " + userGuid + ", " + user_name);
            users.add(new UserEntity(userId, userGuid, user_name));
        }

        connection.close();
        return users;
    }

    @Override
    public UserDefinition add(Integer user_id, String user_guid, String user_name) throws SQLException {
        UserDefinition userDefinition = new UserEntity(user_id, user_guid, user_name);
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(createInsertSQL(user_id, user_guid, user_name));
        connection.close();
        return userDefinition;
    }

    private static String createInsertSQL(Integer user_id, String user_guid, String user_name) {
        return Q_INSERT_USER + user_id + ",'" + user_guid + "'," + "'" + user_name + "')";
    }

    @Override
    public List<UserDefinition> deleteAll() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        boolean execute = statement.execute(Q_DELETE_ALL);

        connection.close();
        return !execute ? Collections.emptyList() : null;
    }
}
