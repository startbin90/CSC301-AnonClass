import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
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
            try {
                if (request.equals("Sign up")) {
                    String info = in.readLine();
                    JSONObject obj = new JSONObject(info);
                    int res = db.signup(obj);
                    out.println(res);
                } else if (request.equals("Log in")) {
                    String info = in.readLine();
                    JSONObject obj = new JSONObject(info);

                    JSONArray ar = db.login(obj);
                    if (ar == null) {
                        out.println(1);
                    } else {
                        out.println(0);
                        out.println(ar);
                    }
                } else if (request.equals("enroll")) {
                    // .....

                } else if (request.equals("ask")) {
                    // broadcast message to all clients
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

                }
            } catch (SQLException unhandled) {
                unhandled.printStackTrace();
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
