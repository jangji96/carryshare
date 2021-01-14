package com.example.jangji.carryshare;

public class getEndDate {
    private String endDate;

    public getEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /*@Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return " " +  endDate + " ";
    }
}