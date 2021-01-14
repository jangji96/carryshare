package com.example.jangji.carryshare;

public class getDate {
    private String startDate;

    public getDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /*@Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return " " + startDate +" ";
    }
}