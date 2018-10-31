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

/* Pass data between Android app and server
*/
public class PassingData {

    private final String host = "100.64.170.86";
    private final int portNumber = 30000;

    private Socket socket;

    public PassingData() {
        try {
            InetAddress address = InetAddress.getByName(host);
            this.socket = new Socket(address, portNumber);
            System.out.println("Successfully connect to server.");

        } catch (IOException e) {
            System.out.println("Connection failed.");
            socket = null;
        }

    }

    //return 1 if success, -1 if failed
    public int SignUp(User user) {

        String[] results = passing("Sign up", user.serialize());

        if (results != null) {
            return Integer.parseInt(results[0]);
        } else {
            return -1;
        }

    }

    // login and display all courses on UI
    public retMsg LogIn(String email, String password) {
        User user = User.getLoginUserObj(email, password);

        String[] results = passing("Log in", user.serialize());

        if (results != null) {
            int error = Integer.parseInt(results[0]);

            User fullUser = User.deSerialize(results[1]);

            return new retMsg(error, fullUser);
        } else {
            return null;
        }
    }

    // display courses registered by given student
    public retMsg DisplayCourses(String email) {

        String[] results = passing("Display courses", "");

        if (results != null) {
            int error = Integer.parseInt(results[0]);

            User user = User.deSerialize(results[1]);

            return new retMsg(error, user);
        } else {
            return null;
        }
    }

    // student add course, success return 0, fail return 1, error return -1
    public int AddCourse(String email, Course course){
        String info = course.serialize();

        String[] results = passing("Add course", info);

        if (results != null) {
            return Integer.parseInt(results[0]);
        } else {
            return -1;
        }

    }


    // instructor create a course, success return 1, fail return -1
    public int CreateCourse(Course course) {

        String[] results = passing("Create course", course.serialize());

        if (results != null) {
            return Integer.parseInt(results[0]);
        } else {
            return -1;
        }


    }

    public int AskingQuestion(String question) {

        String[] results = passing("Ask question", question);

        if (results != null) {
            return Integer.parseInt(results[0]);
        } else {
            return -1;
        }

    }

    private String[] passing(String instruction, String info) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.print(instruction + "\n" + info);

            String[] results = {in.readLine(), in.readLine()};

            return results;

        } catch (IOException e) {
            return null;
        }

    }
}
