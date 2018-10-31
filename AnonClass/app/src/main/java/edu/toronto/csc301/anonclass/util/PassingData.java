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
import java.net.Socket;
import java.util.ArrayList;

/* Pass data between Android app and server
*/
public class PassingData {

    private String host = "host address";
    private int portNumber = 30000;

    private Socket socket;
    Gson gson = new Gson();

    public PassingData() {
        try {
            this.socket = new Socket(host, portNumber);
        } catch (IOException e) {
            socket = null;
        }

    }

    //return 1 if success, -1 if failed
    public int SignUp(User user) {
        String info = user.serialize();

        String result = passing("Sign up", info);

        if (result != null) {
            return Integer.parseInt(result);
        } else {
            return -1;
        }

    }

    // login and display all courses on UI
    public ArrayList<Course> LogIn(String email, String password) {
        User user = User.getLoginUserObj(email, password);

        String info = user.serialize();

        String result = passing("Log in", info);

        if (result != null) {
            Type listType = new TypeToken<ArrayList<Course>>() {}.getType();
            ArrayList<Course> courses = gson.fromJson(result, listType);

            return courses;
        } else {
            return null;
        }
    }

    // display courses registered by given student
    public ArrayList<Course> DisplayCourses(String email) {

        String result = passing("Display courses", "");

        if (result != null) {
            Type listType = new TypeToken<ArrayList<Course>>() {}.getType();
            ArrayList<Course> courses = gson.fromJson(result, listType);

            return courses;
        } else {
            return null;
        }
    }

    // student add course, success return 1, fail return -1
    public int AddCourse(String email, Course course){
        String info = course.serialize();

        String result = passing("Add course", info);
        if (result != null) {
            return Integer.parseInt(result);
        } else {
            return -1;
        }

    }


    // instructor create a course, success return 1, fail return -1
    public int CreateCourse(Course course) {

        String result = passing("Create course", course.serialize());
        if (result != null) {
            return Integer.parseInt(result);
        } else {
            return -1;
        }


    }

    public int AskingQuestion(String question) {

        String result = passing("Ask question", question);

        if (result != null) {
            return Integer.parseInt(result);
        } else {
            return -1;
        }

    }

    private String passing(String instruction, String info) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.print(instruction + "\n" + info);

            return in.readLine();

        } catch (IOException e) {
            return null;
        }

    }
}
