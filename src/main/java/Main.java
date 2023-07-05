import server.BooleanSearchEngine;
import server.PageEntry;
import server.Server;

import java.io.File;
import java.util.List;

public class Main {
    static int port = 8989;
    static String pdfRepo = "pdfs";
    public static String ip = "127.0.0.1";


    public static void main(String[] args) throws Exception {
        BooleanSearchEngine searchWord = new BooleanSearchEngine(new File(pdfRepo));
        List<PageEntry> resultList = searchWord.search("технологии");
        System.out.println(resultList.toString());
        Server server = new Server(port, searchWord);
        server.start();

    }


}


