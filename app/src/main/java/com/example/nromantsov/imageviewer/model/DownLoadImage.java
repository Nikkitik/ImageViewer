package com.example.nromantsov.imageviewer.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.presenter.interfaces.IPresenterAbout;

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
        url = urls[0];

        return ImageUtils.imageDownLoad(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (!isCancelled()) {
            if (iPresenterAbout != null) {
                iPresenterAbout.setBitmap(bitmap);
            } else {
                if (imageView.getTag() != null && imageView.getTag().equals(url))
                    imageView.setImageBitmap(bitmap);
            }
        }
    }
}