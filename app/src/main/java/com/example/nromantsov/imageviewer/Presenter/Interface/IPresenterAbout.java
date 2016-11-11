package com.example.nromantsov.imageviewer.Presenter.Interface;

import android.graphics.Bitmap;
import android.view.View;

import java.util.List;

public interface IPresenterAbout {
    void setUrlTag (String url, String tag);
    List<String> loadUrlsDataBase();

    void setBitmap (Bitmap bitmap);

    void applySnackBar(View view);
    void cancelSnackBar();

    void addDataBase();
    void deleteDataBase();
}
