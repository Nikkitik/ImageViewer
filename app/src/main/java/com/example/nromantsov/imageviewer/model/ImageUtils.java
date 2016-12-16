package com.example.nromantsov.imageviewer.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by n.romantsov on 15.12.2016.
 */

public class ImageUtils {
    private static final String PATH_FILE = "data/data/com.example.nromantsov.imageviewer/";

    public static Bitmap imageDownLoad(String url) {
        Bitmap image = null;

        try {
            image = loadFileStorage(url);
            if (image == null) {
                image = loadFileServer(url);
                saveFile(url, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }


    private static Bitmap loadFileServer(String url) {
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    private static Bitmap loadFileStorage(String url) {
        File file = initFile(url);
        if (!file.exists()) return null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    private static void saveFile(String url, Bitmap image) {
        File file = initFile(url);
        try {
            OutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private static File initFile(String url) {
        int s = url.hashCode();
        File pictureDirectory = new File(PATH_FILE);
        return new File(pictureDirectory, String.valueOf(s));
    }
}
