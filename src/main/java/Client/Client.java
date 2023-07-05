package Client;
import java.io.*;
import java.net.Socket;

public class Client {
    public static String ip = "127.0.0.1";
    public static int port = 8989;

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("бизнес");
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
