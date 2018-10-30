import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
public class MyRunnable implements Runnable {
    private Socket client;

    public MyRunnable(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            AnnonclassDataBase db = new AnnonclassDataBase();
            db.connectDB("jdbc:postgresql://localhost:5432/anonclass");

            String request = in.readLine();

            if (request.equals("Sign up")) {
                String info = in.readLine();
                JSONObject obj = new JSONObject(info);
                try {
                    out.println(db.signup(obj));
                } catch (SQLException sqle) {
                    // exception threw by Database when try to sign up. write -1 to the client.
                    out.println(-1);
                }
            } else if (request.equals("Log in")) {
                String info = in.readLine();
                JSONObject obj = new JSONObject(info);
                try {
                    JSONArray ar = db.login(obj);
                    if (ar == null) {
                        out.println(1);
                    } else {
                        out.println(0);
                        out.println(ar);
                    }
                } catch (SQLException sqle) {
                    // exception threw by Database when try to sign up. write -1 to the client.
                    out.println(-1);
                }
            }
            db.disconnectDB();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e2) {
            // Error when connect to database / disconnect
            e2.printStackTrace();
            System.exit(1);
        }
    }


}
