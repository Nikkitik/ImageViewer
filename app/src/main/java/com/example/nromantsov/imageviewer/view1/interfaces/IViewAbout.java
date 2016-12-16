package com.example.nromantsov.imageviewer.view1.interfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

public interface IViewAbout {
    void colorFloatingActionButton(Boolean result);
    void showSnackBar(View view, String msg);
    Context getContext();
    void loadImage(Bitmap bitmap);
}
