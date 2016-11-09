package com.example.nromantsov.imageviewer.View.Interface;

import java.util.List;

public interface IView {
    void loadUrl(List<String> urls);
    void showProgressbar();
    void hideProgressbar();
}
