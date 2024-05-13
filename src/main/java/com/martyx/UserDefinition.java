package com.martyx;

/**
 * Definition class for {@link UserEntity} .
 */
public interface UserDefinition {

    /**
     * Return the id of  {@link UserEntity} .
     * @return id of the user.
     */
    Integer getUserId();

    /**
     * Return the guid of  {@link UserEntity} .
     * @return guid of the user.
     */
    String getUserGuid();

    /**
     * Return the name of  {@link UserEntity} .
     * @return name of the user.
     */
    String getUserName();
}
