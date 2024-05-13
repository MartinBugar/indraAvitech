package com.martyx;

public class UserEntity implements UserDefinition {

    private final Integer userId;
    private final String userGuid;
    private final String userName;

    public UserEntity(Integer user_id, String user_guid, String user_name) {
        this.userId = user_id;
        this.userGuid = user_guid;
        this.userName = user_name;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public String getUserGuid() {
        return userGuid;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userGuid='" + userGuid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

}
