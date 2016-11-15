package com.example.nromantsov.imageviewer.Presenter;

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Model.Interface.IModel;
import com.example.nromantsov.imageviewer.Model.ParserJSON;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenterMain;
import com.example.nromantsov.imageviewer.View.ApplicationBase;
import com.example.nromantsov.imageviewer.View.Interface.IViewMain;

import java.util.ArrayList;
import java.util.List;

public class UrlListPresenter implements IPresenterMain {
    private IViewMain iViewMain;
    private List<String> tagsList = new ArrayList<>();

    private String tag;
    private String name;
    private int page = 1;

    public UrlListPresenter(IViewMain iViewMain) {
        this.iViewMain = iViewMain;
    }

    @Override
    public void getUrl() {
        switch (name) {
            case "main":
                if (page < 2)
                    iViewMain.showProgressbar();
                new ParserJSON(new IModel() {
                    @Override
                    public void listUrl(List<String> urlList) {
                        iViewMain.loadUrl(urlList);
                        iViewMain.hideProgressbar();
                    }
                }, this).execute();
                break;
            case "favorite":
                DbHandler dbHandler = new DbHandler(ApplicationBase.getContext(), this);
                dbHandler.getUrls(tag);
                break;
            case "favoriteAll":
                dbHandler = new DbHandler(ApplicationBase.getContext(), this);
                dbHandler.getUrlsAll();
                break;
        }
    }

    @Override
    public void setUrlAbout(String url) {
        iViewMain.loadAboutFragment(url, tag);
    }

    @Override
    public void setUrlFavorite(List<String> urls) {
        iViewMain.loadUrl(urls);
    }

    @Override
    public void setTagFavorite(List<String> tags) {
        tagsList = tags;
    }



    @Override
    public void setFragmentName(String name) {
        this.name = name;
    }

    @Override
    public String getFragmentName() {
        return name;
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
    public String getTagFavorite(int position) {
        tag = tagsList.get(position);
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
