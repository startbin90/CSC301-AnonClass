import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.*;
public class MyRunnable implements Runnable {
    private Socket client;
    private Connection connection;

    public MyRunnable(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            AnnonclassDataBase db = new AnnonclassDataBase(connection);
            db.connectDB("jdbc:postgresql://anonclass1.cszu4qtoxymw.us-east-1.rds.amazonaws.com:5432/AnonClass");
            System.out.println("1\n");
            String request = in.readLine();
            System.out.println(request);
            if (request.equals("Sign up")) {
                System.out.println("2\n");
                String info = in.readLine();
                System.out.println(info);
                JSONObject obj = new JSONObject(info);
                if (db.signup(obj)) {
                    out.println("true");
                } else {
                    out.println("false");
                }

            } else if (request.equals("Log in")) {
                String info = in.readLine();
                JSONObject obj = new JSONObject(info);

            }
            db.disconnectDB();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch(SQLException e2) {
            e2.printStackTrace();
            System.exit(1);
        }
    }


}
