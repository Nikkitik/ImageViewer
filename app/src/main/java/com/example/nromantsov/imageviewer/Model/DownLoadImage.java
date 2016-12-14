package com.example.nromantsov.imageviewer.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.presenter.interfaces.IPresenterAbout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DownLoadImage extends AsyncTask<String, Void, Bitmap> {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new StackBlockingQueue<>(128);

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    private IPresenterAbout iPresenterAbout;
    private ImageView imageView;
    private String url;

    public DownLoadImage(IPresenterAbout iPresenterAbout) {
        this.iPresenterAbout = iPresenterAbout;
    }

    public DownLoadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap image = null;
        url = urls[0];
        int s = url.hashCode();

        try {
            File pictureDirectory = new File("data/data/com.example.nromantsov.imageviewer/");
            File file = new File(pictureDirectory, String.valueOf(s));

            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                image = BitmapFactory.decodeStream(inputStream);
            } else {
                InputStream in = new URL(url).openStream();
                image = BitmapFactory.decodeStream(in);

                OutputStream out = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (iPresenterAbout != null) {
            iPresenterAbout.setBitmap(bitmap);
        } else {
            if (imageView.getTag() != null && imageView.getTag().equals(url))
                imageView.setImageBitmap(bitmap);
        }
    }
}