package com.depromeet.match.study.dto;

public final class CreateStudyRequest {
    private String name;
    private String subject;
    private String conductWay;
    private Double latitude;
    private Double longitude;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String end;
    private String start;

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getConductWay() {
        return conductWay;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddr1() {
        return addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }
}
