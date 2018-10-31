package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Course implements EnclosedInfo {
    private String course_code;
    private String course_name;
    private String section_number;
    private String instructor_email;
    private String instructor_name;
    private String locations;

    public Course(String course, String course_name, String section, String email, String instructor, String locations) {
        course_code = course;
        this.course_name = course_name;
        section_number = section;
        instructor_email = email;
        this.instructor_name = instructor;
        this.locations = locations;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getSection_number() {
        return section_number;
    }

    public void setSection_number(String section_number) {
        this.section_number = section_number;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }


    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
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
