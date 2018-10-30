package edu.toronto.csc301.anonclass.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/* Pass data between Android app and server
*/
public class PassingData {

    private String host = "whatever";
    private int portNumber = 30000;

    private Socket socket;

    public PassingData() {
        try {
            this.socket = new Socket(host, portNumber);
        } catch (IOException e) {
            socket = null;
        }

    }

    //return 1 if success, -1 if failed
    public int SignUp(User user) {
        Gson gson = new GsonBuilder().create();
        String info = gson.toJson(user);

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.print("Sign up\n" + info);
            String result = in.readLine();

            return Integer.parseInt(result);

        } catch (IOException e) {
            return -1;
        }

    }

    //return 1 if success, -1 if failed
    public ArrayList<Course> LogIn(String email, String password) {
        User user = new User(email, password);

        Gson gson = new GsonBuilder().create();
        String info = gson.toJson(user);

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.print("Log in\n" + info);
            String result = in.readLine();

            Type listType = new TypeToken<ArrayList<Course>>() {}.getType();
            ArrayList<Course> courses = gson.fromJson(result, listType);

            return courses;

        } catch (IOException e) {
            return null;
        }
    }

    // display courses registered by given student
    public ArrayList<Course> DisplayCourses(String email) {
        Gson gson = new GsonBuilder().create();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.print("Display Courses\n");
            String result = in.readLine();

            Type listType = new TypeToken<ArrayList<Course>>() {}.getType();
            ArrayList<Course> courses = gson.fromJson(result, listType);

            return courses;

        } catch (IOException e) {
            return null;
        }
    }

    public int AddCourse(String email, Course course) {

        return 0;

    }

    public void AskingQuestion(String question) {


    }

    private BufferedReader passing(String info) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(info);

            return in;
        } catch (IOException e) {
            return null;
        }
    }

}
