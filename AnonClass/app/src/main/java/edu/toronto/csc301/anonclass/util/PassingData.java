package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/* Pass data between Android app and server
*/
public class PassingData {

    private static final String host = "100.64.170.86";
    private static final int portNumber = 30000;

    private static Gson gson = new GsonBuilder().create();

    //return 1 if success, -1 if failed
    public static int SignUp(User user) {

        ArrayList<String> results = passing("Sign up", 1, user.serialize());

        if (results != null) {
            return Integer.parseInt(results.get(0));
        } else {
            return -1;
        }

    }

    // login and display all courses on UI
    public static retMsg LogIn(String email, String password) {
        User user = User.getLoginUserObj(email, password);

        ArrayList<String> results = passing("Log in", 2, user.serialize());

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return new retMsg(error, null);
            }

            User fullUser = User.deSerialize(results.get(1));

            return new retMsg(error, fullUser);
        } else {
            return null;
        }
    }

    // display courses registered by given student
    public static retMsg DisplayCourses(String email) {

        ArrayList<String> results = passing("Display courses", 2, "");

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return new retMsg(error, null);
            }

            User user = User.deSerialize(results.get(1));

            return new retMsg(error, user);
        } else {
            return null;
        }
    }

    // student add course, success return 0, fail return 1, error return -1
    public static int EnrolCourse(String email, Course course){
//        String info = course.serialize();

        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("email", email);
        infoMap.put("course_name", course.getCourse_name());
        infoMap.put("section_number", course.getSection_number());

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("enrol", 1,info);

        if (results != null) {
            return Integer.parseInt(results.get(0));
        } else {
            return -1;
        }

    }


    // instructor create a course, success return 1, fail return -1
    public static int CreateCourse(Course course) {

        ArrayList<String> results = passing("create", 1, course.serialize());

        if (results != null) {
            return Integer.parseInt(results.get(0));
        } else {
            return -1;
        }


    }

    // ask question
    public static int AskingQuestion(String question) {

        ArrayList<String> results = passing("Ask question", 1, question);

        if (results != null) {
            return Integer.parseInt(results.get(0));
        } else {
            return -1;
        }

    }


    // return an array of information
    // index 0 is error flag, index 1 is returned Json string
    private static ArrayList<String> passing(String instruction, int length, String info) {

        try {
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, portNumber);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println(instruction + "\n" + info);


            ArrayList<String> results = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                results.add(in.readLine());
            }

            return results;

        } catch (IOException e) {
            System.out.println("Connection failed.");
            return null;
        }

    }
}
