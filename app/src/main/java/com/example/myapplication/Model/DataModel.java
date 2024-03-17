package com.example.myapplication.Model;

public class DataModel {
    private String Name,department,location,subject,topic,key,minutes,endDateTime,currentDateTime,dateTime,startedTime;

     //private String Name,department,location,subject,topic,key,minutes,endDateTime,currentTime,endTime,dateTime;

    public DataModel() {
    }

    public DataModel(String name, String department, String location, String subject, String topic, String key, String minutes, String endDateTime, String currentDateTime, String dateTime, String startedTime) {
        Name = name;
        this.department = department;
        this.location = location;
        this.subject = subject;
        this.topic = topic;
        this.key = key;
        this.minutes = minutes;
        this.endDateTime = endDateTime;
        this.currentDateTime = currentDateTime;
        this.dateTime = dateTime;
        this.startedTime = startedTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }
}
