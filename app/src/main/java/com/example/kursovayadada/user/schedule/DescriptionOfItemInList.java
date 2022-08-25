package com.example.kursovayadada.user.schedule;

public class DescriptionOfItemInList {
    private String teacher;
    private String subject;
    private String time;
    private String day;

    public DescriptionOfItemInList(String teacher, String subject, String time, String day) {
        this.teacher = teacher;
        this.subject = subject;
        this.time = time;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
