package org;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/*
 * Обрабатываем входящий Json
 *
 */


public class ConvertJson {
    private final Map<String, String> rawPostJson;

    public ConvertJson(BufferedReader reader) throws Exception400 {
        Gson gson = new Gson();
        try {
            rawPostJson = gson.fromJson(reader, Map.class);
        } catch (JsonParseException jsonEx) {
            throw new Exception400();
        }
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
