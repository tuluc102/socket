import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public Client() throws Exception {
    Socket socket = new Socket("localhost", 1234);
    Scanner scanner = new Scanner(System.in);
    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

    while (true) {
      System.out.print("Client: ");
      String messageToSend = scanner.nextLine();
      toServer.writeBytes(messageToSend + "\n");

      if (messageToSend.equals("exit")) {
        socket.close();
        break;
      }

      String receivedMessage = fromServer.readLine();
      System.out.println("Server: " + receivedMessage);
    }
  }

  public static void main(String[] args) throws Exception {
    new Client();
  }
}
