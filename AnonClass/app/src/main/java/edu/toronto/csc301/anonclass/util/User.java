package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;

public class User implements EnclosedInfo{
    private String email;
    private String pwdHash;
    private String utorid;
    private String firstName;
    private String lastName;
    private Boolean studentFlag;

    private Set<Course> courses = new HashSet<>();

    public static User getLoginUserObj(String email, String pwd) {
        return new User(email, pwd);
    }

    public static User getRegisterUserObj(String email, String pwd, String UTORid,
                                          String firstName, String lastName, boolean isStudent) {
        return new User(email, pwd, UTORid, firstName, lastName, isStudent);
    }

    public static User userFromServer(String email, String UTORid,
                                          String firstName, String lastName, boolean isStudent) {
        return new User(email, UTORid, firstName, lastName, isStudent);
    }

    private User(String email, String password) {
        this.email = email;
        this.pwdHash = HashPassword.hash(password);
    }

    private User(String email, String utorid,
                String firstName, String lastName, boolean studentFlag) {
        this.email = email;
        this.utorid = utorid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentFlag = studentFlag;
    }
    private User(String email, String pwd, String utorid,
                 String firstName, String lastName, boolean studentFlag) {
        this.email = email;
        this.pwdHash = HashPassword.hash(pwd);
        this.utorid = utorid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentFlag = studentFlag;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtorid() {
        return utorid;
    }

    public void setUtorid(String utorid) {
        this.utorid = utorid;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = HashPassword.hash(pwdHash);
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

    public Boolean getStudentFlag() {
        return studentFlag;
    }

    public void setStudentFlag(Boolean teacher) {
        studentFlag = teacher;
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

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public EnclosedInfo deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, User.class);
    }
}
