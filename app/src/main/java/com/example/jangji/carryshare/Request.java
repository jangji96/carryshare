package com.example.jangji.carryshare;

public class Request {
    private String rentID;
    private String startDate;
    private String endDate;
    private String request;

    public Request(String request) {

        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return  "요청  :" + request  ;
//                ", Latitude='" + Latitude + '\'' + '\n' +
//                ", Longitude='" + Longitude + '\'' + '\n' +

    }
}