package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class SerializableInfo {
    public String serialize(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

}
