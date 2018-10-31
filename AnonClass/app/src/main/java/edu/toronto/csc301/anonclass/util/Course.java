package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course implements EnclosedInfo {
    private String courseCode;
    private String courseName;
    private String sectionNum;
    private String instructorName;
    private Date time;
    private String location;

    public static List<Course> getDummyCourses(){
        List<Course> lst = new ArrayList<>();
        lst.add(new Course("csc301","Intro to Software Engineering",
                "L0101", "Mark", new Date(), "BA 1210"));
        lst.add(new Course("csc324","Principle of Programming Language",
                "L0101", "David", new Date(), "SS 1234"));
        return lst;
    }

    public Course(String courseCode, String courseName, String sectionNum, String instructorName, Date time, String location) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNum = sectionNum;
        this.instructorName = instructorName;
        this.time = time;
        this.location = location;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSectionNum() {
        return sectionNum;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public Date getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static Course deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, Course.class);
    }
}
