package com.example.jangji.carryshare;

public class MapMarker {
    private String size;
    private String type;
    private String cost;
    private String userID;

    public MapMarker(String userID, String size, String type, String cost) {
        this.userID = userID;
        this.size = size;
        this.type = type;
        this.cost = cost;
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
        return  userID +'\n'+ size +  '\n' + type + '\n' + cost;
    }
}