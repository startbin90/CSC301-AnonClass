package Server;
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

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            AnnonclassDataBase db = new AnnonclassDataBase(connection);
            db.connectDB("");
            String request = in.readLine();
            if (request.equals("Sign up")) {
                String info = in.readLine();
                JSONObject obj = new JSONObject(info);
                if (db.signup(obj)) {
                    out.write("true");
                } else {
                    out.write("false");
                }

            } else if (request.equals("Log in")) {
                String info = in.readLine();
                JSONObject obj = new JSONObject(info);

            }
            db.disconnectDB();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
