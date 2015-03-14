package utils;

import com.google.gson.Gson;
import entity.template.StoreTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: louis.forite
 * Date: 14/03/15
 * Time: 12:00
 */
public class JsonFileReader {

    private static Gson gson = new Gson();
    private static final String storesFileLocation = "stores.json";

    /**
     * Load from JSON all the store templates
     *
     * @return the full list of information related to stores
     */
    public static List<StoreTemplate> loadStoreTemplates() {
        try {
            String fileName = JsonFileReader.class.getClassLoader().getResource(storesFileLocation).getFile();
            Reader reader = new FileReader(fileName);
            return Arrays.asList(gson.fromJson(reader, StoreTemplate[].class));

        } catch (FileNotFoundException e) {
            System.err.println(storesFileLocation + " not found");
        }
        return new ArrayList<>(0);
    }
}
