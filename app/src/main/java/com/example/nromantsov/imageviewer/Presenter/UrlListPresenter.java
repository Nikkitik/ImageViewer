package com.example.nromantsov.imageviewer.Presenter;

import com.example.nromantsov.imageviewer.Model.Interface.IModel;
import com.example.nromantsov.imageviewer.Model.ParserJSON;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenter;
import com.example.nromantsov.imageviewer.View.Interface.IView;

import java.util.ArrayList;
import java.util.List;

public class UrlListPresenter implements IPresenter {
    private IView iView;

    private List<String> urls = new ArrayList<>();
    private String tag;

    public UrlListPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getUrl() {
        iView.showProgressbar();
        new ParserJSON(new IModel() {
            @Override
            public void listUrl(List<String> urlList) {
                for (int i = 0; i < urlList.size(); i++) {
                    urls.add(urlList.get(i));
                }
                iView.loadUrl(urls);
                iView.hideProgressbar();
            }
        }, this).execute();
    }

    @Override
    public void setTag(String tag) {
        if (tag == null || tag.isEmpty())
            tag = "cat";

        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
