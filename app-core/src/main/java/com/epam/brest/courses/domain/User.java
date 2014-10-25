package com.epam.brest.courses.domain;

//smth new from Idea
public class User{




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

    public User(Long userId, String login, String userName) {
        this.userId = userId;
        this.login = login;
        this.userName = userName;
    }
    public User(){

    }

    private String login;
    private String userName;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
