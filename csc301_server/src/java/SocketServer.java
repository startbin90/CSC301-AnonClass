import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private int portNumber = 30000;
    private ServerSocket serverSocket;

    public SocketServer() {

    }
    public void runserver() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                MyRunnable n = new MyRunnable(clientSocket);
                n.run();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }
}
