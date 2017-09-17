package com.bbc;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Task implements Runnable {
    private String url;

    public Task(String url) {
        this.url = url;
    }

    public void run() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(10000);

            //Handle permanently removed sites.
            if(con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ) {
                String redirect = con.getHeaderField("Location");
                if (redirect != null) {
                    con = (HttpURLConnection) new URL(redirect).openConnection();
                    System.out.println("Redirected automatically "+url);
                }
            }

            getInfo(con);

        } catch (SocketTimeoutException e) {
            JSONConverter.createErrorJSON(url, "Request timed out automatically after 10 seconds");
            System.err.println("Request automatically timed out");
        }  catch (MalformedURLException e) {
            JSONConverter.createErrorJSON(url, "Malformed url");
            System.err.println("Malformed URL");
        } catch (IOException e) {
            JSONConverter.createErrorJSON(url, "Non-existent URL");
            System.err.println("Url does not exist");
        }
    }

    private void getInfo(HttpURLConnection con) throws IOException {
        String statusCode = con.getResponseCode()+"";
        Map<String, List<String>> map = con.getHeaderFields();

        List<String> contentLength = map.get("Content-Length");
        //Handle some chunk-encoded sites that do not contain content-length.
        if (contentLength == null) {
            contentLength = new ArrayList<>();
            contentLength.add("Content-Length missing");
        }

        String dateTime = map.get("Date").get(0);

        JSONConverter.createValidJSON(url, statusCode, contentLength.get(0), dateTime);
    }
}
