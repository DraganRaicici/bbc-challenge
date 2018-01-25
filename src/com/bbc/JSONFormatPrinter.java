package com.bbc;

import org.json.*;

public class JSONFormatPrinter {
    public static void createValidJSON(String url, String statusCode, String contentLength, String date) {
        JSONObject json = new JSONObject();
        json.put("Url", url);
        json.put("Status_code", statusCode);
        json.put("Content_length", contentLength);
        json.put("Date", date);

        System.out.println(json.toString(4));
    }

    public static void createErrorJSON(String url, String errorReason) {
        JSONObject json = new JSONObject();
        json.put("Url", url);
        json.put("Error", errorReason);

        System.out.println(json.toString(4));
    }
}
