package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;

import java.util.List;

public class retMsg extends SerializableInfo {

    private int errorCode;
    private User user;
    private List<Course> searched;
    private List<Question> questions;
    private String stringExtra;

    public static retMsg getStringExtraRet(int err, String str){
        retMsg ret = new retMsg();
        ret.setErrorCode(err);
        ret.setStringExtra(str);
        return ret;
    }
    public static retMsg getQuestionsRet(int err, List<Question> questions){
        retMsg ret = new retMsg();
        ret.setErrorCode(err);
        ret.setQuestions(questions);
        return ret;
    }
    public static retMsg getErrorRet(int err){
        retMsg ret = new retMsg();
        ret.setErrorCode(err);
        return ret;
    }

    public static retMsg getUserRet(int err, User user){
        retMsg ret = new retMsg();
        ret.setErrorCode(err);
        ret.setUser(user);
        return ret;
    }

    public static retMsg getSearchedRet(int err, List<Course> lst){
        retMsg ret = new retMsg();
        ret.setErrorCode(err);
        ret.setSearched(lst);
        return ret;
    }

    public void setStringExtra(String stringExtra) {
        this.stringExtra = stringExtra;
    }

    public List<Question> getQuestions() {

        return questions;
    }

    public String getStringExtra() {
        return stringExtra;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<Course> getSearched() {
        return searched;
    }

    public User getUser() {
        return user;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSearched(List<Course> searched) {
        this.searched = searched;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public static retMsg deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, retMsg.class);
    }
}
