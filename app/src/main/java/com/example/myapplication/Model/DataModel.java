package com.example.myapplication.Model;

public class DataModel {

    String Name;


    String subject;
    String department;
    String topic;



    String location;
    String minutes;
    String currentTime;

    public DataModel(){

    }
//    public DataModel(String teacherName, String subject, String department, String topic, String room, String minutes, String currentTime) {
//        this.teacherName = teacherName;
//        this.subject = subject;
//        this.department = department;
//        this.topic = topic;
//        this.room = room;
//        this.minutes = minutes;
//        this.currentTime = currentTime;
//    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
