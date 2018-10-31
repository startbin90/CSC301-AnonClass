package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class retMsg implements EnclosedInfo {

    private int errorCode;
    private User user;

    public int getErrorCode() {
        return errorCode;
    }

    public User getUser() {
        return user;
    }

    public retMsg(int errorCode, User user) {
        this.errorCode = errorCode;
        this.user = user;
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
