package com.jainamd.shiksha.utils;

public class Course {
    private String courseId;
    private String name;
    private  int attendance;
    private int marks;

    public Course(String courseId, String name, int attendance, int marks) {
        this.courseId = courseId;
        this.name = name;
        this.attendance = attendance;
        this.marks = marks;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
