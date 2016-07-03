package com.blogspot.nipunswritings.lkweather.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nipun on 6/28/16.
 */
public class HttpClient {



    private HttpDataProcessor mDataProcessor;
    private String mUrl;

    public HttpClient (String url, HttpDataProcessor dataProcessor) {
        mUrl = url;
        mDataProcessor = dataProcessor;
    }

    public boolean performGetRequest() {

        int responseCode;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        boolean done = false;

        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                done = mDataProcessor.processReceivedData(inputStream);

            }
        } catch (IOException exception) {

            done = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException exc){

            }
        }

        return done;

    }

}
