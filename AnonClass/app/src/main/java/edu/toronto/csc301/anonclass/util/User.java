package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User implements EnclosedInfo{
    private String email;
    private String password;
    private String UTORid;
    private String firstName;
    private String lastName;
    private Boolean isStudent;

    private List<Course> courses = new ArrayList<>();

    public static User getLoginUserObj(String email, String pwd) {
        return new User(email, pwd);
    }

    public static User getRegisterUserObj(String email, String pwd, String UTORid,
                                          String firstName, String lastName, boolean isStudent) {
        return new User(email, pwd, UTORid, firstName, lastName, isStudent);
    }

    public static User userFromServer(String email, String UTORid,
                                          String firstName, String lastName, boolean isStudent,
                                      List<Course> courses) {
        return new User(email, UTORid, firstName, lastName, isStudent, courses);
    }

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private User(String email, String UTORid,
                String firstName, String lastName, boolean isStudent, List<Course> courses) {
        this.email = email;
        this.UTORid = UTORid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isStudent = isStudent;
        this.courses = courses;
    }
    private User(String email, String pwd, String UTORid,
                 String firstName, String lastName, boolean isStudent) {
        this.email = email;
        this.password = pwd;
        this.UTORid = UTORid;
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

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object user2) {
        return user2 instanceof User && ((User) user2).email.equals(this.email);
    }

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, User.class);
    }
}
