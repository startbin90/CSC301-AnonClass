package edu.toronto.csc301.anonclass.util;


public class Session {
    String email;
    Course course;
    float latitude;
    float longitude;

    public static Session requestSeesion(String email, Course course, float latitude, float longitude){
        return new Session(email, course, latitude, longitude);
    }
    private Session(String email, Course course, float latitude, float longitude) {
        this.email = email;
        this.course = course;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
