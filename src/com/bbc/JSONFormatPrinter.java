package com.bbc;

import org.json.*;

public class JSONFormatPrinter {
    public static JSONObject createValidJSON(String url, String statusCode, String contentLength, String date) {
        JSONObject json = new JSONObject();
        json.put("Url", url);
        json.put("Status_code", statusCode);
        json.put("Content_length", contentLength);
        json.put("Date", date);

        return json;
    }

    public static JSONObject createInvalidJSON(String url, String errorReason) {
        JSONObject json = new JSONObject();
        json.put("Url", url);
        json.put("Error", errorReason);

        return json;
    }

    public static void printJSON(JSONObject json){
        System.out.println(json.toString(4));
    }
}
