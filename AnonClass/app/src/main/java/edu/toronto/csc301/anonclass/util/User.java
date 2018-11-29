package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class User extends SerializableInfo {
    private String email;
    private String pwdHash;
    private String utorid;
    private String firstName;
    private String lastName;
    private Boolean studentFlag;

    private List<Course> courses = new ArrayList<>();

    public interface UserBuilder{
        User build();
    }
    public static class LoginUserBuilder implements UserBuilder{
        private String email;
        private String pwdHash;

        public LoginUserBuilder email(String email){
            this.email = email;
            return this;
        }
        public LoginUserBuilder pwd(String pwd){
            this.pwdHash = HashPassword.hash(pwd);
            return this;
        }
        public User build(){
            // TODO: validate attributes here
            return new User(this);
        }
    }

    public static class RegisterUserBuilder implements UserBuilder{
        private String email;
        private String pwdHash;
        private String UTORid;
        private String firstName;
        private String lastName;
        private Boolean studentFlag;

        public RegisterUserBuilder email(String email){
            this.email = email;
            return this;
        }
        public RegisterUserBuilder pwd(String pwd){
            this.pwdHash = HashPassword.hash(pwd);
            return this;
        }

        public RegisterUserBuilder UTORid(String UTORid){
            this.UTORid = UTORid;
            return this;
        }

        public RegisterUserBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public RegisterUserBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public RegisterUserBuilder studentFlag(Boolean studentFlag){
            this.studentFlag = studentFlag;
            return this;
        }

        public User build(){
            // TODO: validate attributes here
            return new User(this);
        }
    }

    public static User fakeUserFromServer(String email, String UTORid,
                                          String firstName, String lastName, boolean isStudent,
                                          List<Course> courses) {
        return new User(email, UTORid, firstName, lastName, isStudent, courses);
    }

    private User(UserBuilder builder) {
        if (builder instanceof LoginUserBuilder){

            this.email = ((LoginUserBuilder) builder).email;
            this.pwdHash = ((LoginUserBuilder) builder).pwdHash;

        } else if (builder instanceof RegisterUserBuilder){
            this.email = ((RegisterUserBuilder) builder).email;
            this.pwdHash= ((RegisterUserBuilder) builder).pwdHash;
            this.utorid = ((RegisterUserBuilder) builder).UTORid;
            this.firstName = ((RegisterUserBuilder) builder).firstName;
            this.lastName = ((RegisterUserBuilder) builder).lastName;
            this.studentFlag = ((RegisterUserBuilder) builder).studentFlag;
        }
    }

    private User(String email, String UTORid,
                String firstName, String lastName, boolean isStudent, List<Course> courses) {
        this.email = email;
        this.utorid = UTORid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentFlag = isStudent;
        this.courses = courses;

    }
    private User(String email, String pwd, String UTORid,
                 String firstName, String lastName, boolean isStudent, List<Course> courses) {
        this.email = email;
        this.pwdHash = pwd;
        this.utorid = UTORid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentFlag = isStudent;
        this.courses = courses;

    }

    public String getPwdHash() {
        return pwdHash;
    }
    public String getEmail() {
        return email;
    }

    public String getUtorid() {
        return utorid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getStudentFlag() {
        return studentFlag;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object user2) {
        return user2 instanceof User && ((User) user2).email.equals(this.email);
    }


    public static User deSerialize(String Json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(Json, User.class);
    }


    public static void main(String[] args) {
        String str = new User.RegisterUserBuilder().email("csc3012@test.com").pwd("1234").
                firstName("fi").lastName("adfa").UTORid("daf2d").studentFlag(true).build().serialize();
        System.out.println(str);
        User us = new GsonBuilder().create().fromJson(str, User.class);
    }
}
