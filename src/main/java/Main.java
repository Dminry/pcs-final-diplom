import server.BooleanSearchEngine;
import server.Server;

import java.io.File;

public class Main {
    static int port = 8989;
    static String pdfRepo = "pdfs";

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(new File(pdfRepo));
        Server server = new Server(port, booleanSearchEngine);
        server.start();
    }
}