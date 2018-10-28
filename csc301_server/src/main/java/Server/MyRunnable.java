package Server;
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
                String email = in.readLine();
                String psw = in.readLine();
                if (db.signup(email, psw)) {
                    out.write(1);
                } else {
                    out.write(-1);
                }

            } else if (request.equals("Log in")) {
                String email = in.readLine();
                String psw = in.readLine();
                if (db.login(email, psw)) {
                    out.write(1);
                } else {
                    out.write(-1);
                }
            }
            db.disconnectDB();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
