package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Course extends SerializableInfo {

    private int course_id;
    private String course_code;
    private String course_name;
    private String section_number;
    private String instructor_email;
    private String instructor_name;
    private Date time_created;
    private String locations;

    public static List<Course> getEnrolledDummyCourses(){
        List<Course> lst = new ArrayList<>();
        lst.add(new Course(1, "csc301","Intro to Software Engineering",
                "L0101", "emailM","Mark", new Date(), "BA 1210"));
        lst.add(new Course(2,"csc324","Principle of Programming Language",
                "L0101", "emailD","David", new Date(), "SS 1234"));
        return lst;
    }

    public static List<Course> getUpdatedEnrolledDummyCourses(){
        List<Course> lst = new ArrayList<>();
        lst.add(new Course(1, "csc301","Intro to Software Engineering",
                "L0101", "emailM","Mark", new Date(), "BA 1210"));
        lst.add(new Course(2,"csc324","Principle of Programming Language",
                "L0101", "emailD","David", new Date(), "SS 1234"));
        lst.add(new Course(3,"csc369","Operating System",
                "L0101", "emailD","Karen", new Date(), "LM 120"));
        return lst;
    }

    public static List<Course> getSearchedDummyCourses(){
        List<Course> lst = new ArrayList<>();
        lst.add(new Course(1, "csc301","Intro to Software Engineering",
                "L0101", "emailM","Mark", new Date(), "BA 1210"));
        lst.add(new Course(2,"csc301","Intro to Software Engineering",
                "L0501", "emailD","David", new Date(), "BA 1234"));
        return lst;
    }

    public static List<Course> getTeachersDummyCourses(){
        List<Course> lst = new ArrayList<>();
        lst.add(new Course(1, "csc301","Intro to Software Engineering",
                "L0501", "emailM","Mark", new Date(), "BA 1210"));
        lst.add(new Course(2,"csc309","Programming on the Web",
                "L0101", "emailD","Mark", new Date(), "BA 1234"));
        return lst;
    }



    public static Course getCreatedCourse(String course_code, String course_name, String section,
                                          String email, String instructor, String locations){
        return new Course(course_code, course_name, section, email, instructor, new Date(), locations);
    }

    private Course(String course, String course_name, String section, String email, String instructor, Date time_created, String locations) {
        this.course_code = course;
        this.course_name = course_name;
        this.section_number = section;
        this.instructor_email = email;
        this.instructor_name = instructor;
        this.time_created = time_created;
        this.locations = locations;
    }

    private Course(int course_id, String course_code, String course_name, String section_number, String instructor_email, String instructor_name, Date time_created, String locations) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.section_number = section_number;
        this.instructor_email = instructor_email;
        this.instructor_name = instructor_name;
        this.time_created = time_created;
        this.locations = locations;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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

    public String getTime_created() {
        return translate(time_created);
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }

    public String translate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String s = "";
        if (1 <= month && month <= 4) {
            s = s + "Winter ";
        } else if (5 <= month && month <= 8) {
            s = s + "Summer ";
        } else if (9 <= month && month <= 12) {
            s = s + "Fall ";
        }
        s = s + String.format(Locale.CANADA, "%d", year);
        return s;
    }


    public static Course deSerialize(String Json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(Json, Course.class);
    }
}
