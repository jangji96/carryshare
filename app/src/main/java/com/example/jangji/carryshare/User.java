package com.example.jangji.carryshare;

public class User {
    //private String userID;
    private String userName;
    private String userPhone;

    public User(String userName, String userPhone) {
        //this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
    }

//    public String getuserID() {
//        return userID;
//    }

//    public void setuserID(String userID) {
//        this.userID = userID;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return " 이름 = " + userName + " ]" +  '\n' +
                " [ 전화번호 = " + userPhone + " ";
    }
}