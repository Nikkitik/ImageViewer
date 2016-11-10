package com.example.nromantsov.imageviewer.Presenter;

import com.example.nromantsov.imageviewer.Model.Interface.IModel;
import com.example.nromantsov.imageviewer.Model.ParserJSON;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenter;
import com.example.nromantsov.imageviewer.View.Interface.IView;

import java.util.ArrayList;
import java.util.List;

public class UrlListPresenter implements IPresenter {
    private IView iView;

    private String tag;
    private int page = 1;

    public UrlListPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getUrl() {
        iView.showProgressbar();
        new ParserJSON(new IModel() {
            @Override
            public void listUrl(List<String> urlList) {
                iView.loadUrl(urlList);
                iView.hideProgressbar();
            }
        }, this).execute();
    }

    @Override
    public void setTag(String tag) {
        if (tag == null || tag.isEmpty())
            tag = "popular";

        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void setPage(int page) {
        this.page = ++page;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public void loadData() {
        setPage(page);
        getUrl();
    }
}
