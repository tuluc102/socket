import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientClock extends JFrame {

  private final JLabel timeLabel;

  public ClientClock() {
    setTitle("Client Time");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 100);
    setLocationRelativeTo(null);

    timeLabel = new JLabel("", JLabel.CENTER);
    timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(timeLabel);

    setVisible(true);

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        getTimeFromServer();
      }
    }, 0, 1000);
  }

  private void getTimeFromServer() {
    Thread thread = new Thread(() -> {
      try (Socket socket = new Socket("localhost", 12345);
          DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
          BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
        toServer.writeBytes("time\n");
        String message = fromServer.readLine();
        if (message != null) {
          SwingUtilities.invokeLater(() -> timeLabel.setText(message));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(ClientClock::new);
  }
}
