package com.example.kursovayadada.models;

public class Shedules {
    private Long id;
    private String groups;
    private String time;
    private String subject;
    private String teacher;
    private String dayOfWeek;
    private String parityOfWeek;

    public Shedules(Long id, String groups, String time, String subject, String teacher, String dayOfWeek, String parityOfWeek) {
        this.id = id;
        this.groups = groups;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
        this.dayOfWeek = dayOfWeek;
        this.parityOfWeek = parityOfWeek;
    }

    public Shedules(String groups, String time, String subject, String teacher, String dayOfWeek, String parityOfWeek) {
        this.groups = groups;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
        this.dayOfWeek = dayOfWeek;
        this.parityOfWeek = parityOfWeek;
    }

    public String getParityOfWeek() {
        return parityOfWeek;
    }

    public void setParityOfWeek(String parityOfWeek) {
        this.parityOfWeek = parityOfWeek;
    }

    public Shedules() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "Shedules{" +
                "id=" + id +
                ", groups='" + groups + '\'' +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", parityOfWeek='" + parityOfWeek + '\'' +
                '}';
    }
}
