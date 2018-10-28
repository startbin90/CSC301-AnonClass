package baseFunctions;

import org.json.JSONObject;

public class Course {
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
    public String toString() {
        return (new JSONObject(this)).toString();
    }

}
