package com.example.nromantsov.imageviewer.Presenter.Interface;

import java.util.List;

public interface IPresenterAbout {
    void setUrlTag (String url, String tag);
    Boolean getFlag();
    List<String> loadUrlsDataBase();

    void addDataBase();
    void deleteDataBase();
}
