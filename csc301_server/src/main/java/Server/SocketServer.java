package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private int portNumber = 30000;
    private ServerSocket serverSocket;

    public void runserver() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new MyRunnable(clientSocket)).start();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }
}
