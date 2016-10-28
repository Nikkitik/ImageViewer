package com.example.nromantsov.imageviewer.AsyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nromantsov.imageviewer.Interface.ISourceArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON extends AsyncTask<String, Integer, Void> {

    private String resultJSON;
    private List<String> sourceArray = new ArrayList<>();
    private ProgressBar progressBar;
    private ISourceArray iSourceArray;
    private int page;
    private String tag;

    public ParserJSON(ISourceArray iSourceArray, ProgressBar progressBar, int page, String tag) {
        this.iSourceArray = iSourceArray;
        this.progressBar = progressBar;
        this.page = page;
        this.tag = tag;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=4fe038fa52c229b33d82da3283567c6c&tags=" + tag + "&license=&page=" + page + "&format=json&nojsoncallback=1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJSON = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultJSON);
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

                String source = "https://farm6.staticflickr.com/" + serverName + "/" + idName + "_" + secretName + ".jpg";
                sourceArray.add(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (iSourceArray != null) {
            iSourceArray.sourceLoader(sourceArray);
        }

        progressBar.setVisibility(View.GONE);
    }
}