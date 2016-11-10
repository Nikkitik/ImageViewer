package com.example.nromantsov.imageviewer.Presenter;

import com.example.nromantsov.imageviewer.Model.Interface.IModel;
import com.example.nromantsov.imageviewer.Model.ParserJSON;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenter;
import com.example.nromantsov.imageviewer.View.Interface.IViewMain;

import java.util.List;

public class UrlListPresenter implements IPresenter {
    private IViewMain iViewMain;

    private String tag;
    private int page = 1;

    public UrlListPresenter(IViewMain iViewMain) {
        this.iViewMain = iViewMain;
    }

    @Override
    public void getUrl() {
        iViewMain.showProgressbar();
        new ParserJSON(new IModel() {
            @Override
            public void listUrl(List<String> urlList) {
                iViewMain.loadUrl(urlList);
                iViewMain.hideProgressbar();
            }
        }, this).execute();
    }

    @Override
    public void setUrlAbout(String url) {
        iViewMain.loadAboutFragment(url, tag);
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
