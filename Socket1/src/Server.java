import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
  public Server() throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    System.out.println("Server is running on port 1234");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      Socket socket = serverSocket.accept();
      System.out.println("Client connected");
      BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

      while (true) {
        String receivedMessage = fromClient.readLine();
        if (receivedMessage == null || receivedMessage.equals("exit")) {
          System.out.println("Client disconnected");
          socket.close();
          break;
        }
        System.out.println("Client: " + receivedMessage);
        System.out.print("Server: ");
        String messageToSend = scanner.nextLine();
        toClient.writeBytes(messageToSend + "\n");
      }
    }
  }

  public static void main(String[] args) throws Exception {
    new Server();
  }
}
