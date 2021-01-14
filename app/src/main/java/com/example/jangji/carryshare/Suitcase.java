package com.example.jangji.carryshare;

public class Suitcase {
    private String size;
    private String type;
    private String cost;
    //    private String Latitude;
//    private String Longitude;
    private String userID;

    public Suitcase(String userID, String size, String type, String cost) {
        this.userID = userID;
        this.size = size;
        this.type = type;
        this.cost = cost;
//        this.Latitude = latitude;
//        this.Longitude = longitude;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

//    public String getLatitude() {
//        return Latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        Latitude = latitude;
//    }
//
//    public String getLongitude() {
//        return Longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        Longitude = longitude;
//    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return  "아이디 : " + userID +   '\n' +
                "사이즈 : " + size +  '\n' +
                " 종류   : " + type +  '\n' +
                " 요금   : " + cost  + "원";
//                ", Latitude='" + Latitude + '\'' + '\n' +
//                ", Longitude='" + Longitude + '\'' + '\n' +

    }
}