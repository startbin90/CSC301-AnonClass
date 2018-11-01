import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class SocketServer {
    private int portNumber = 30000;
    private ServerSocket serverSocket;
    private HashSet<Socket> clients = new HashSet<Socket>();
    public void runServer() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                MyRunnable n = new MyRunnable(clientSocket, clients);
                new Thread(n).start();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }
}
