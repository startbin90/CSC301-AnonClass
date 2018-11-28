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
    private int session;

    public static List<Question> getDummyQuestions(){
        List<Question> lst = new ArrayList<>();
        lst.add(new Question("davy@test.com", "i don't understand \n could u please explain it again?", new Date(), 1));
        lst.add(new Question("anthony@test.com", "davy, don't interrupt teacher!", new Date(), 2));
        return lst;
    }

    public Question(String email, String question, Date when, int session) {
        this.email = email;
        this.question = question;
        this.when = when;
        this.session = session;
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

    public int getSession() {
        return session;
    }

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
