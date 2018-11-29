package edu.toronto.csc301.anonclass.util;


public class Session extends SerializableInfo{
    String email;
    int course_id;
    double latitude;
    double longitude;

    public static Session requestSession(String email, int course_id, double latitude, double longitude){
        return new Session(email, course_id, latitude, longitude);
    }
    private Session(String email, int course_id, double latitude, double longitude) {
        this.email = email;
        this.course_id = course_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
