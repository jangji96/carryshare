package com.example.jangji.carryshare;

public class User_id {
    private String userName;

    public User_id(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*@Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "userName=" + userName ;
    }
}