import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerTime {

  public static void main(String[] args) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(12345)) {
      System.out.println("Server is running...");
      while (true) {
        try (Socket socket = serverSocket.accept();
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream())) {
          String clientMessage = fromClient.readLine();
          if (clientMessage != null && clientMessage.equals("time")) {
            String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n";
            toClient.writeBytes(message);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
