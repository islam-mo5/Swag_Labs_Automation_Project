package Utilities;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*

Data handling class
using any wanted files + helper functions

*/
public class DataUtilities {

    // getJsonData func dynamic هنحط مسار الفولدر عشان بعد كده اقدر استخدم اي ملف جواه فتبقى ال
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";

    //TODO: Reading data from JSON file

    public static String getJsonData(String fileName , String key) // params --> file + field
    {
        try {
            //define object of FileReader
            FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json");

            //parse Json to Json element
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        }
        catch (Exception e){
            e.printStackTrace();}
        return "";
    }



    //TODO: Reading data from properties file

    public static String getPropertyValue(String filename, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH + filename + ".properties"));
        return properties.getProperty(key);

    }

}
