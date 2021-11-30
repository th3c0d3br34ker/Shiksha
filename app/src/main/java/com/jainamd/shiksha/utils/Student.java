package com.jainamd.shiksha.utils;

public class Student {
    private String courseCode;
    private String account_id;
    private String marks;
    private String attendance;
    private String uuid;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Student(String account_id) {
        this.account_id = account_id;
    }

    public String getAccountId() {
        return account_id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setAccountId(String account_id) {
        this.account_id = account_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
