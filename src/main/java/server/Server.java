package server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    BooleanSearchEngine booleanSearchEngine;
    PageEntryListSerializer pageEntryListSerializer;
    private int port;

    public Server(int port, BooleanSearchEngine booleanSearchEngine) {
        this.port = port;
        this.booleanSearchEngine = booleanSearchEngine;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);) { // стартуем сервер один(!) раз
            System.out.println("Сервер запустился");
            while (true) { // в цикле(!) принимаем подключения
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                    System.out.println("Новый клиент подключился");
                    String clientRequest = in.readLine();
                    List<PageEntry> wordsStatistics = null;
                    try {
                        wordsStatistics = booleanSearchEngine.search(clientRequest);
                    } catch (NullPointerException ex) {
                        out.println(ex.getMessage());
                    }
                    pageEntryListSerializer = new PageEntryListSerializer(wordsStatistics);
                    out.println(pageEntryListSerializer.writeJsonToFile("data.json"));

                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
