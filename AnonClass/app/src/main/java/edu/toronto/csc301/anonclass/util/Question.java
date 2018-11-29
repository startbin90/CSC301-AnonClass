package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question implements SerializableInfo {

    private String email;
    private String question;
    private Date when;
    private int course_id;

    public static List<Question> getDummyQuestions(){
        List<Question> lst = new ArrayList<>();
        lst.add(new Question("davy@test.com", "i don't understand \n could u please explain it again?", new Date(), 1));
        lst.add(new Question("anthony@test.com", "davy, don't interrupt teacher!", new Date(), 2));
        return lst;
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

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
