package com.example.jangji.carryshare;

public class registerRequest {
    private String rentID;
    private String startDate;
    private String endDate;
    private String request;

    public registerRequest(String rentID, String startDate, String endDate, String request) {
        this.rentID = rentID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.request = request;
    }

    public String getRegisterID() {
        return rentID;
    }

    public void setRegisterID(String registerID) {
        this.rentID = registerID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
//
//    public String getLongitude() {
//        return Longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        Longitude = longitude;
//    }

//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }

    @Override
    public String toString() {
        return  "아이디 : " + rentID +  '\n' +
                "시작일 : " + startDate +  '\n' +
                "끝일  : " + endDate +  '\n' +
                "요청  :" + request  ;
//                ", Latitude='" + Latitude + '\'' + '\n' +
//                ", Longitude='" + Longitude + '\'' + '\n' +

    }
}