package com.martyx;

import java.sql.SQLException;
import java.util.List;

/**
 * Service for {@link CommandServiceBean} .
 */
public interface CommandService {

    /**
     * Print all users from the database to console.
     * @return all users from database.
     */
    List<UserDefinition> printAll() throws SQLException;

    /**
     * Delete all users from the database.
     * @return all users from database. After deletion, it should be empty List.
     */
    List<UserDefinition> deleteAll() throws SQLException;

    /**
     * Insert user to the database.
     * @return User inserted to Database.
     *
     * @param userId
     * @param userGuid
     * @param userName
     */
    UserDefinition add(Integer userId, String userGuid, String userName) throws SQLException;
}
