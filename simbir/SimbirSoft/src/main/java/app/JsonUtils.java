package app;


import java.time.LocalDateTime;
import java.util.*;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {

    public static void saveToJsonDB(String key, Map<String, Long> map) {

        JSONObject obj = new JSONObject();
        FileWriter fileWriter = null;
        obj.put(key, map);
        obj.put("DATE", LocalDateTime.now().toString().substring(0, 19)); //Добавляем информацию о дате запроса

        try  {
            fileWriter = new FileWriter("jsondb.db", true);
            fileWriter.write(obj.toJSONString());

        } catch (IOException e) {
            ExceptionUtils.writeExceptionToFile(e);
            //e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                ExceptionUtils.writeExceptionToFile(e);
                //e.printStackTrace();
            }
        }
    }
}
