package edu.toronto.csc301.anonclass.util;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

    private static final String host = "192.168.0.23";
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
    public static retMsg LogIn(User user) {


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

        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("email", email);

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("Display enrolled courses", 2, info);

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

            Type listType = new TypeToken<ArrayList<Course>>() {
            }.getType();
            ArrayList<Course> courses = gson.fromJson(results.get(1), listType);

            return retMsg.getSearchedRet(0, courses);
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    // student add course, success return 0, fail return 1, error return -1
    public static retMsg EnrolCourse(String email, int id) {

        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("email", email);
        infoMap.put("course_id", id);

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("Enroll", 1, info);

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    // get status of this session
    public static retMsg GetStatus(int courseId) {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("id", courseId);

        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("get status", 2, info);

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(1);
            }

            return retMsg.getStringExtraRet(error, results.get(2));
        } else {
            return retMsg.getErrorRet(-1);
        }
    }

    // student join the session
    public static retMsg JoinSession(Session session) {


        String info = session.serialize();

        ArrayList<String> results = passing("Join Session", 1, info);

        if (results != null) {
            return retMsg.getErrorRet(Integer.parseInt(results.get(0)));
        } else {
            return retMsg.getErrorRet(-1);
        }
    }

    // ask question
    public static retMsg AskingQuestion(Question question) {

        ArrayList<String> results = passing("Ask Question", 2, question.serialize());

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(1);
            }

            Type listType = new TypeToken<ArrayList<Question>>() {
            }.getType();
            ArrayList<Question> questions = gson.fromJson(results.get(1), listType);

            return retMsg.getQuestionsRet(0, questions);
        } else {
            return retMsg.getErrorRet(-1);
        }

    }

    // refresh question room page
    public static retMsg RefreshQuestionRoom(int id) {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("course_id", id);
        String info = gson.toJson(infoMap);

        ArrayList<String> results = passing("Refresh", 2, info);

        if (results != null) {
            int error = Integer.parseInt(results.get(0));
            if (error == 1) {
                return retMsg.getErrorRet(1);
            }

            Type listType = new TypeToken<ArrayList<Question>>() {
            }.getType();
            ArrayList<Question> questions = gson.fromJson(results.get(1), listType);

            return retMsg.getQuestionsRet(0, questions);
        } else {
            return retMsg.getErrorRet(-1);
        }
    }

    // helper
    // return an array of information
    // index 0 is error flag, index 1 is returned Json string
    private static ArrayList<String> passing(String instruction, int length, String info) {

        try {
            Socket socket = new Socket(host, portNumber);

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
