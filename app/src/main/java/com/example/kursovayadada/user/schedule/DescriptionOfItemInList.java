package com.example.kursovayadada.user.schedule;

public class DescriptionOfItemInList {

    private Long id;
    private String teacher;
    private String groups;
    private String subject;
    private String time;
    private String dayOfWeek;
    private String parityOfWeek;
    private String typeOfActivity;

    @Override
    public String toString() {
        return "DescriptionOfItemInList{" +
                "id=" + id +
                ", teacher='" + teacher + '\'' +
                ", groups='" + groups + '\'' +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", parityOfWeek='" + parityOfWeek + '\'' +
                ", typeOfActivity='" + typeOfActivity + '\'' +
                '}';
    }

    public DescriptionOfItemInList(Long id, String teacher, String groups, String subject, String time, String dayOfWeek, String parityOfWeek, String typeOfActivity) {
        this.id = id;
        this.teacher = teacher;
        this.groups = groups;
        this.subject = subject;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.parityOfWeek = parityOfWeek;
        this.typeOfActivity = typeOfActivity;
    }

    public DescriptionOfItemInList(String teacher, String groups, String subject, String time, String dayOfWeek, String parityOfWeek, String typeOfActivity) {
        this.teacher = teacher;
        this.groups = groups;
        this.subject = subject;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.parityOfWeek = parityOfWeek;
        this.typeOfActivity = typeOfActivity;
    }

    public DescriptionOfItemInList() {
    }

    public String getParityOfWeek() {
        return parityOfWeek;
    }

    public void setParityOfWeek(String parityOfWeek) {
        this.parityOfWeek = parityOfWeek;
    }

    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public void setTypeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
