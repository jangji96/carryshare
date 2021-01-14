package com.example.jangji.carryshare;

public class MarkerItem {

    double lat;
    double lon;
    private String size;
    private String type;
    private String cost;
    private String userID;

    public MarkerItem(String userID, String size,String type, String cost, double lat, double lon){

        this.lat = lat;
        this.lon = lon;
        this.size = size;
        this.type = type;
        this.cost = cost;
        this.userID = userID;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
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
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return  userID +'\n'+ size +  '\n' + type + '\n' + cost + '\n' + lat + '\n' + lon;
    }
}
