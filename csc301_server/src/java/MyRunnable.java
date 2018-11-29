import com.mysql.cj.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

public class MyRunnable implements Runnable {
    private Socket client;
    private HashSet<Socket> writers;
    public MyRunnable(Socket client, HashSet writers) {
        // current client
        this.client = client;
        // all clients that have connected to the server
        this.writers = writers;
    }

    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            AnnonclassDataBase db = new AnnonclassDataBase();
            db.connectDB("jdbc:postgresql://localhost:5432/anonclass");
            String request = in.readLine();
            System.out.println(request);
            JSONObject info  = new JSONObject(in.readLine());
            System.out.println(info);
            try {
                if (request.equals("Sign up")) {
                    int res = db.signup(info);
                    out.println(res);
                } else if (request.equals("Log in")) {
                    JSONObject user = db.login(info);
                    if (user == null) {
                        out.println(1);
                        out.println("Error");
                    } else {
                        out.println(0);
                        out.println(user);
                    }
                } else if (request.equals("Enroll")) {
                    int res = db.enroll_course(info);
                    out.println(res);
                } else if (request.equals("Create")) {
                    //Create course. Connection from web.
                    out.println(db.addCourse(info));
                } else if (request.equals("Ask Question")) {
                    int course_id = info.getInt("course_id");
                    SessionInfo sessionInfo = SessionStorage.getSessionInfo(Integer.toString(course_id));
                    if (sessionInfo == null) {
                        System.out.println("sessionInfo null");
                        out.println(1);
                    } else {
                        SessionStorage.addSessionQuestion(Integer.toString(course_id), info);
                        out.println(0);
                        List<JSONObject> questions = SessionStorage.getQuestions(Integer.toString(course_id));
                        JSONArray ret= new JSONArray();
                        for (JSONObject q:questions) {
                            ret.put(q);
                        }
                        out.println(ret);
                    }

                } else if (request.equals("Display courses")) {
                    JSONArray ar = db.display(info);
                    if (ar.isEmpty()) {
                        out.println(1);
                        out.println("Error");
                    } else {
                        out.println(0);
                        out.println(ar);
                    }
                } else if (request.equals("Display enrolled courses")) {
                    JSONObject user = db.display_enrolled_courses(info);
                    out.println(0);
                    out.println(user);
                } else if (request.equals("Open Session")) {
                    //TO DO (Connection from web)
                } else if (request.equals("Join Session")) {
                    int course_id = info.getInt("course_id");
                    String email = info.getString("email");
                    double latitude = info.getDouble("latitude");
                    double longitude = info.getDouble("longitude");
                    Tuple location = new Tuple(latitude, longitude);
                    SessionInfo sessionInfo = SessionStorage.getSessionInfo(Integer.toString(course_id));
                    if (sessionInfo == null) {
                        out.println(1);
                    } else {
                        SessionStorage.StudentJoin(Integer.toString(course_id), email, location);
                        out.println(0);
                    }
                } else if (request.equals("Refresh")) {
                    int course_id = info.getInt("course_id");
                    List<JSONObject> questions = SessionStorage.getQuestions(Integer.toString(course_id));
                    JSONArray ret= new JSONArray();
                    for (JSONObject q:questions) {
                        ret.put(q);
                    }
                    out.println(0);
                    out.println(ret);

                }
            } catch (SQLException unhandled) {
                unhandled.printStackTrace();
            } catch (ParseException unhandled2) {
                unhandled2.printStackTrace();
            }
            db.disconnectDB();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e2) {
            // Error when connect to database
            e2.printStackTrace();
            System.exit(1);
        }
    }
}
