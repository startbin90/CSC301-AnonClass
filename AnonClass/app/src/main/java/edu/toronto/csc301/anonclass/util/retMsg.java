package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class retMsg implements EnclosedInfo {

    private int errorCode;
    private User user;
    private List<Course> searched;

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

    @Override
    public String serialize() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static retMsg deSerialize(String Json) {
        Gson gson = new Gson();
        return gson.fromJson(Json, retMsg.class);
    }
}
