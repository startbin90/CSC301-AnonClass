package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question implements EnclosedInfo {

    private String email;
    private String question;
    private Date when;

    public static List<Question> getDummyQuestions(){
        List<Question> lst = new ArrayList<>();
        lst.add(new Question("davy@test.com", "i don't understand \n could u please explain it again?", new Date()));
        lst.add(new Question("anthony@test.com", "davy, don't interrupt teacher!", new Date()));
        return lst;
    }
    public Question(String email, String question, Date when) {
        this.email = email;
        this.question = question;
        this.when = when;
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

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
