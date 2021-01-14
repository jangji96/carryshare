package com.example.jangji.carryshare;

public class LatiLongi {

    private String Latitude;
    private String Longitude;


    public LatiLongi(String Latitude, String Longitude) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    @Override
    public String toString() {
        return  "위도 : " + Latitude +  '\n' +
                "경도 : " + Longitude ;

//                ", Latitude='" + Latitude + '\'' + '\n' +
//                ", Longitude='" + Longitude + '\'' + '\n' +

    }
}