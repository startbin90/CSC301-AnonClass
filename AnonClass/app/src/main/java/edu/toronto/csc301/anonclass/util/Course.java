package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Course implements EnclosedInfo {
    private String courseCode;
    private String sectionCode;
    private String instructor;
    private String location;

    public Course(String course, String section, String instructor, String location) {
        courseCode = course;
        sectionCode = section;
        this.instructor = instructor;
        this.location = location;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public EnclosedInfo deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, Course.class);
    }
}
