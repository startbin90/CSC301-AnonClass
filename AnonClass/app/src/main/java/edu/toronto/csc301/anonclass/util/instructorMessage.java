package edu.toronto.csc301.anonclass.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class instructorMessage extends SerializableInfo {
    private String instructor;
    private Date date;
    private String message;

    public static List<instructorMessage> getDummyMessages(){
        List<instructorMessage> mes = new ArrayList<>();
        mes.add(new instructorMessage("Karen", "Hi,\n" +
                "\n" +
                "Assignment 4 has been posted on Quercus, and the appropriate MarkUs repos are available. \n" +
                "\n" +
                "Regards,\n" +
                "\n" +
                "Karen Reid\n" +
                "\n" +
                "P.S.  My office has moved to BA 4224"));
        mes.add(new instructorMessage("Karen", "Hi,\n" +
                "\n" +
                "I have updated the FS exercise 3 handout." +
                "\n" +
                "Karen"));
        return mes;
    }
    public instructorMessage(String instructor, String message){
        this.instructor = instructor;
        this.date = new Date();
        this.message = message;
    }

    public String getInstructor() {
        return instructor;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
