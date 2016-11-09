package com.example.nromantsov.imageviewer.Presenter.Interface;

public interface IPresenter {
    void getUrl();

    void setTag(String tag);
    String getTag();

    void setPage(int page);
    Integer getPage();
}
