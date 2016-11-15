package com.example.nromantsov.imageviewer.Presenter.Interface;

import java.util.List;

public interface IPresenterMain {
    void getUrl();
    void setUrlAbout(String url);
    void setUrlFavorite(List<String> urls);
    void setTagFavorite(List<String> tags);

    void setFragmentName(String name);
    String getFragmentName();

    void setTag(String tag);
    String getTag();
    String getTagFavorite(int position);

    void setPage(int page);
    Integer getPage();

    void loadData();
}
