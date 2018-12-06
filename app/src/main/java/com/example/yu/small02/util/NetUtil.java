package com.example.yu.small02.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtil {
    public static String getString(String str) throws IOException {
        URL url = new URL(str);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);
        int responseCode = urlConnection.getResponseCode();
        if(responseCode==200){
            String input = getInput(urlConnection.getInputStream());
            return input;
        }
        return null;
    }

    private static String getInput(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        for(String rmp=reader.readLine();rmp!=null;rmp=reader.readLine()){
            builder.append(rmp);
        }
        return builder.toString();
    }
}
