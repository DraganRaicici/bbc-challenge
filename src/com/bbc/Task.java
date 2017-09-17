package com.bbc;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Task implements Runnable {
    private String url;

    public Task (String url) {
        this.url = url;
    }
    public void run() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            getInfo(con);

        } catch (ProtocolException e) {
            //createError(url, "invalid protocol");
            System.err.println("Protocol exception");
        } catch (MalformedURLException e) {
           // createError(url, "malformed url");
            System.err.println("Malformed URL");
        } catch(SocketTimeoutException e){
            System.err.println("Timed-out");
        }
        catch (IOException e) {
            //createError(url, "Non-existant URL");
            System.err.println("Url does not exist");
        }
    }

//    private void createError(String url, String errorReason) {
//        MyJsonErrorFormat errorJson = new MyJsonErrorFormat(url, errorReason);
//        System.out.println(errorJson.toString());
//    }

    private void getInfo(HttpURLConnection con) throws IOException {
        int statusCode = con.getResponseCode();
        Map<String, List<String>> map = con.getHeaderFields();

        List<String> contentLength = map.get("Content-Length");
        if (contentLength == null) {
            contentLength = new ArrayList<>();
            contentLength.add("'Content-Length' missing");
        }

        String dateTime = map.get("Date").get(0);

        //createJson(url, statusCode, contentLength.get(0), dateTime);
    }

//    private void createJson(String url, int statusCode, String contentLength, String dateTime) {
//        MyJsonFormat json = new MyJsonFormat(url, statusCode + "", contentLength, dateTime);
//        System.out.println(json.toString());
//    }

}
