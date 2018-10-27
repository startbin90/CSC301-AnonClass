package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
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
            AnnonclassDataBase db = new AnnonclassDataBase(connection);
            db.connectDB("");
            String request = in.readLine();
            if (request == "Sign up") {
                String email = in.readLine();
                String psw = in.readLine();
                db.signup(email, psw);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
