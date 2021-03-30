package org;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class ConvertJson {
    private final Map<String, String> rawPostJson;


    public ConvertJson(BufferedReader reader) {
        Gson gson = new Gson();
        rawPostJson = gson.fromJson(reader, Map.class);
    }

    private String getJsonFrom() {
        String from = rawPostJson.get("from");
        return from.replaceAll(" ", "");
    }

    private String getJsonTo() {
        String to = rawPostJson.get("to");
        return to.replaceAll(" ", "");
    }

    public Map<String, String> getMapPost() {
        Map<String, String > mapPost = new HashMap<>();
        mapPost.put("to", getJsonTo());
        mapPost.put("from", getJsonFrom());
        return mapPost;
    }
}
