package com.example.nromantsov.imageviewer.View.Interface;

import java.util.List;

public interface IViewMain {
    void loadUrl(List<String> urls);

    void loadAboutFragment(String url, String tag);

    void showProgressbar();
    void hideProgressbar();
}
