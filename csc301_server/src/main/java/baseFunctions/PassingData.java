package baseFunctions;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

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
    public int SignUp(String email, String password) {
        User newUser = new User(email, password);
        StringBuilder info = new StringBuilder();
        info.append("Sign up\n");
        info.append(newUser.toString());

        JSONObject obj = new JSONObject();


        BufferedReader in = passing(info.toString());
        if (in != null) {
            try {
                return Integer.parseInt(in.readLine());
            } catch (IOException e) {
                return -1;
            }
        }

        return -1;

    }

    //return 1 if success, -1 if failed
    public int LogIn(String email, String password) {
        StringBuilder info = new StringBuilder();
        info.append("Sign up\n");
        info.append(String.format("%s\n%s\n", email, password));

        BufferedReader in = passing(info.toString());
        if (in != null) {
            try {
                return Integer.parseInt(in.readLine());
            } catch (IOException e) {
                return -1;
            }
        }

        return -1;
    }

    // display courses registered by given student
    public String DisplayCourses(String email) {
        StringBuilder info = new StringBuilder();
        info.append("Display Courses\n");
        info.append(String.format("%s\n%s\n", email));

        BufferedReader in = passing(info.toString());
        if (in != null) {
            try {
                String courses;
                while ((courses = in.readLine()) != null) {
                }

            } catch (IOException e) {
                return null;
            }
        }

        return null;
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
