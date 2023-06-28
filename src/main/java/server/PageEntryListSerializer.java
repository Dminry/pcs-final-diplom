package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PageEntryListSerializer {
    List<PageEntry> list;

    public PageEntryListSerializer(List<PageEntry> list) {
        this.list = list;
    }
    public String writeJsonToFile(String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list.toArray());
        System.out.println(json);
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);

        }

        return json;
    }



}
