package com.epam.brest.courses.domain;

/**
 * Created by fieldistor on 03.11.14.
 */
public class UserImpl implements  User{




    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!userId.equals(other.getUserId()))
            return false;
        if (!getUserName().equals(other.getUserName()))
            return false;
        if (!getLogin().equals(other.getLogin()))
            return false;
        return true;
    }


    private Long userId;

    public UserImpl(Long userId, String login, String userName) {
        this.userId = userId;
        this.login = login;
        this.userName = userName;
    }
    public UserImpl(){

    }

    private String login;
    private String userName;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
