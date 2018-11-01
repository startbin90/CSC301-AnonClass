package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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

    //return 0 if success, 1 if failed, -1 if error
    public static retMsg SignUp(User user) {

        ArrayList<String> results = passing("Sign up", 1, user.serialize());

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    // login and display all courses on UI
    public static retMsg LogIn(String email, String password) {
        User user = User.getLoginUserObj(email, password);

        ArrayList<String> results = passing("Log in", 2, user.serialize());

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(error);
            }

            User fullUser = User.deSerialize(results.get(1));

            return retMsg.getUserRet(error, fullUser);
        } else {
            return retMsg.getErrorRet(-1);
        }
    }

    // display courses registered by given student
    public static retMsg DisplayCourses(String email) {

        ArrayList<String> results = passing("Display enrolled courses", 2, "");

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(error);
            }

            User user = User.deSerialize(results.get(1));

            return retMsg.getUserRet(error, user);
        } else {
            return retMsg.getErrorRet(-1);
        }
    }

    public static retMsg ShowRelatedCourses(String course_code) {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("course_code", course_code);

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("Display courses", 2, info);

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(1);
            }

            Type listType = new TypeToken<ArrayList<Course>>() {}.getType();
            ArrayList<Course> courses = gson.fromJson(results.get(1), listType);

            return retMsg.getSearchedRet(0, courses);
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    // student add course, success return 0, fail return 1, error return -1
    public static retMsg EnrolCourse(String email, int id){

        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("email", email);
        infoMap.put("course_id", id);

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("enroll", 1, info);

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
        }

    }


    // instructor create a course, success return 1, fail return -1
    public static retMsg CreateCourse(Course course) {

        ArrayList<String> results = passing("create", 1, course.serialize());

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
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


    // TODO: keep receiving questions from server
    public static void QuestionRoom() {

        try {
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, portNumber);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            ReceiveQuestions receiveQuestions = ReceiveQuestions.getInstance(in, true);
            receiveQuestions.run();


        } catch (IOException e) {
            System.out.println("Connection failed.");
        }
    }

    // ask question
    public static retMsg AskingQuestion(String question) {

        ArrayList<String> results = passing("Ask question", 1, question);

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    private static class ReceiveQuestions implements Runnable {

        private BufferedReader in;
        private boolean inRoom;

        public static ReceiveQuestions getInstance(BufferedReader in, boolean inRoom) {
            return new ReceiveQuestions(in, inRoom);
        }


        private ReceiveQuestions(BufferedReader in, boolean inRoom){
            this.in = in;
            this.inRoom = inRoom;

        }

        public void exitRoom() {
            inRoom = false;
        }

        public void askQuestions() {

        }

        @Override
        public void run() {
            try {
                while(inRoom){
                    String question = in.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
