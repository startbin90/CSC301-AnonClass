import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.util.HashSet;

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
            JSONObject info  = new JSONObject(in.readLine());
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
                } else if (request.equals("enroll")) {
                    out.println(db.enroll_course(info));
                } else if (request.equals("create")) {
                    out.println(db.addCourse(info));
                } else if (request.equals("ask")) {
                    try {
                        StringBuilder s = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            s.append(line + '\n');
                        }
                        for (Socket writer : writers) {
                            PrintWriter out2 = new PrintWriter(new OutputStreamWriter(writer.getOutputStream())
                                    , true);
                            out2.println(s);
                        }
                    } catch (IOException disconnected) {
                        // socket disconnect
                        System.out.println("disconnected");
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
