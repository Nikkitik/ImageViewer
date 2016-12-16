package com.example.nromantsov.imageviewer.model1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.romantsov on 16.12.2016.
 */

class JSONUtils {

    private static final String URL_TAG = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=4fe038fa52c229b33d82da3283567c6c&tags=";
    private static final String URL_PAGE = "&license=&page=";
    private static final String URL_FORMAT = "&format=json&nojsoncallback=1";
    private static final String METHOD_REQUEST = "GET";

    private static final String FLICKR_URL = "https://farm6.staticflickr.com/";
    private static final String FLICKR_FORMAT = "_b.jpg";

    static String connectionURL(String tag, int page) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL(URL_TAG + tag + URL_PAGE + page + URL_FORMAT);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(METHOD_REQUEST);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    static List<String> getSourceArray(String result) {
        List<String> sourceArray = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject photos = jsonObject.getJSONObject("photos");

            JSONArray photoArray = photos.getJSONArray("photo");
            String idName;
            String serverName;
            String secretName;
            for (int i = 0; i < photoArray.length(); i++) {
                JSONObject id = photoArray.getJSONObject(i);
                idName = id.getString("id");
                serverName = id.getString("server");
                secretName = id.getString("secret");

                String source = FLICKR_URL + serverName + "/" + idName + "_" + secretName + FLICKR_FORMAT;
                sourceArray.add(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceArray;
    }
}
