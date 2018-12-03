package edu.toronto.csc301.anonclass.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question extends SerializableInfo {

    private String email;
    private String question;
    private Date when;
    private int course_id;

    private static List<Question> dummyQuestions = new ArrayList<>();
    public static List<Question> getDummyQuestions(){
        dummyQuestions.add(new Question("davy@test.com", "i don't understand \n could u please explain it again?", new Date(), 1));
        dummyQuestions.add(new Question("anthony@test.com", "davy, don't interrupt teacher!", new Date(), 2));
        return dummyQuestions;
    }
    public static void addDummyQuestion(Question ques){
        dummyQuestions.add(ques);
    }

    public Question(String email, String question, Date when, int course_id) {
        this.email = email;
        this.question = question;
        this.when = when;
        this.course_id = course_id;
    }

    public String getEmail() {
        return email;
    }

    public Date getWhen() {
        return when;
    }

    public String getQuestion() {
        return question;
    }

    public int getCourse_id() {
        return course_id;
    }

}
