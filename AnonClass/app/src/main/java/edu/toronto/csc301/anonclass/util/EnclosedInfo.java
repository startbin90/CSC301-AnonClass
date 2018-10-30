package edu.toronto.csc301.anonclass.util;

public interface EnclosedInfo {
    String serialize();
    EnclosedInfo deSerialize(String Json);

}
