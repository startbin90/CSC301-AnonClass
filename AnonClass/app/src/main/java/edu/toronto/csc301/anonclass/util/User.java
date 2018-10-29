package edu.toronto.csc301.anonclass.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
    private String email;
    private String UTORid;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isStudent;

    private Set<Course> courses = new HashSet<Course>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String UTORid, String password,
                String firstName, String lastName, boolean isStudent) {
        this.email = email;
        this.UTORid = UTORid;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isStudent = isStudent;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUTORid() {
        return UTORid;
    }

    public void setUTORid(String UTORid) {
        this.UTORid = UTORid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean teacher) {
        isStudent = teacher;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object user2) {
        return user2 instanceof User && ((User) user2).email.equals(this.email);
    }
}
