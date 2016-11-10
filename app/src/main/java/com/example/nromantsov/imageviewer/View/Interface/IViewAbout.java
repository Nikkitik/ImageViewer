package com.example.nromantsov.imageviewer.View.Interface;

import android.content.Context;
import android.view.View;

public interface IViewAbout {
    void colorFloatingActionButton(Boolean result);
    void showSnackBar(View view, String msg);
    Context getContext();
}
