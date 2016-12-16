package com.example.nromantsov.imageviewer.view.interfaces;

import java.util.List;

public interface IViewMain {
    void loadUrl(List<String> urls);

    void loadAboutFragment(String url, String tag);

    void showProgressbar();
    void hideProgressbar();
}
