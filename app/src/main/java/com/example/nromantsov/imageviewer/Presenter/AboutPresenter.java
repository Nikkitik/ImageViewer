package com.example.nromantsov.imageviewer.Presenter;

import android.app.Activity;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Model.DownLoadImage;
import com.example.nromantsov.imageviewer.Model.UrlBase;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenterAbout;
import com.example.nromantsov.imageviewer.View.Interface.IViewAbout;

import java.util.List;

public class AboutPresenter implements IPresenterAbout {
    private IViewAbout iViewAbout;
    private Activity activity;
    private ImageView imageView;

    private DbHandler dbHandler;
    private String tag, url;
    private List<String> urlList;
    private Boolean flag = false;

    public AboutPresenter(IViewAbout iViewAbout, Activity activity, ImageView imageView) {
        this.iViewAbout = iViewAbout;
        this.activity = activity;
        this.imageView = imageView;
    }

    @Override
    public void setUrlTag(String url, String tag) {
        this.tag = tag;
        this.url = url;
        imageView.setTag(url);
        new DownLoadImage(imageView).execute(url);
    }

    @Override
    public Boolean getFlag() {
        return flag;
    }

    @Override
    public List<String> loadUrlsDataBase() {
        dbHandler = new DbHandler(activity.getApplicationContext());
        urlList = dbHandler.getUrls(tag);

        for (int i = 0; i < urlList.size(); i++) {
            if (url.equals(urlList.get(i))) {
                flag = true;
                break;
            }
            flag = false;
        }
        iViewAbout.colorFloatingActionButton(flag);

        return urlList;
    }

    @Override
    public void addDataBase() {
        dbHandler.addUrl(new UrlBase(tag, url));
        loadUrlsDataBase();
    }

    @Override
    public void deleteDataBase() {
        dbHandler.deleteUrlFavorite(url);
        loadUrlsDataBase();
    }
}
