package com.martyx;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Unit test for simple App.
 * Test if commands "Add" , PrintAll, DeleteAll are working.
 */
public class AppTest extends TestCase {

    @Test
    public void testInsertUserWithParameters() throws SQLException, InterruptedException {
        //Prepare database connection
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        //Clear database before test
        statement.execute("DELETE FROM susers");

        // Prepare testing scenario
        SynchronousQueue<String> queueCommands = new SynchronousQueue<>(true);
        App.addCommandToQueue(queueCommands, "Add(1, \"a1\",\"Martin\")");
        App.dequeue(queueCommands);


        ResultSet resultSet = statement.executeQuery("SELECT * FROM susers");

        //Test
        while (resultSet.next()) {
            assertEquals(1, resultSet.getInt("user_id"));
            assertEquals("a1", resultSet.getString("user_guid"));
            assertEquals("Martin", resultSet.getString("user_name"));
        }

        //Clear database after test
        statement.execute("DELETE FROM susers");
        connection.close();
    }

    @Test
    public void testPrintAllCommand() throws SQLException, InterruptedException {
        //Prepare database connection
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        //Clear database before test
        statement.execute("DELETE FROM susers");

        //Insert two records to DB for testing purposes
        String insert1 = "INSERT INTO susers (user_id, user_guid, user_name) VALUES (1, 'a1', 'Martin')";
        String insert2 = "INSERT INTO susers (user_id, user_guid, user_name) VALUES (2, 'a2', 'Robert')";
        statement.execute(insert1);
        statement.execute(insert2);

        // Prepare testing scenario
        SynchronousQueue<String> queueCommands = new SynchronousQueue<>(true);
        App.addCommandToQueue(queueCommands, "PrintAll");
        List<UserDefinition> userDefinitions = App.dequeue(queueCommands);

        assertEquals(2, userDefinitions.size());
        assertEquals("UserEntity{userId=1, userGuid='a1', userName='Martin'}", userDefinitions.get(0).toString());
        assertEquals("UserEntity{userId=2, userGuid='a2', userName='Robert'}", userDefinitions.get(1).toString());

        //Clear database after test
        statement.execute("DELETE FROM susers");
        connection.close();
    }

    @Test
    public void testDeleteAllUsers() throws SQLException, InterruptedException {
        //Prepare database connection
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        //Clear database before test
        statement.execute("DELETE FROM susers");

        //Insert two records to DB for testing purposes
        String insert1 = "INSERT INTO susers (user_id, user_guid, user_name) VALUES (1, 'a1', 'Martin')";
        String insert2 = "INSERT INTO susers (user_id, user_guid, user_name) VALUES (2, 'a2', 'Robert')";
        statement.execute(insert1);
        statement.execute(insert2);

        // Prepare testing scenario
        SynchronousQueue<String> queueCommands = new SynchronousQueue<>(true);
        App.addCommandToQueue(queueCommands, "DeleteAll");
        List<UserDefinition> userDefinitions = App.dequeue(queueCommands);

        assertEquals(0, userDefinitions.size());

        //Clear database after test
        statement.execute("DELETE FROM susers");
        connection.close();
    }

    private static Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:h2:~/test";
        String username = "sa";
        String password = "";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 embedded database.");
        return connection;
    }
}
