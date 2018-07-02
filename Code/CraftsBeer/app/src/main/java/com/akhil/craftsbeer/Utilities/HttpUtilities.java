package com.akhil.craftsbeer.Utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aghatiki on 6/30/2018.
 */

public class HttpUtilities {
    private static final String NAME = HttpUtilities.class.getName();

    public HttpUtilities() {

    }

    /**
     * common http method that can be reused in the application where ever required.
     */
    public String callService(String a_url) {
        String response = null;
        try {
            URL url = new URL(a_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = convertResponse(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * This method convert the reponse to string using string builder.
     */
    private String convertResponse(InputStream a_in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(a_in));
        StringBuilder stringBuilder = new StringBuilder();

        String a_data;

        try {
            while ((a_data = reader.readLine()) != null) {
                stringBuilder.append(a_data);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                a_in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
