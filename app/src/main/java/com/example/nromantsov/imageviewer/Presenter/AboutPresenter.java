package com.example.nromantsov.imageviewer.presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.example.nromantsov.imageviewer.model.DbHandler;
import com.example.nromantsov.imageviewer.model.DownLoadImage;
import com.example.nromantsov.imageviewer.model.UrlBase;
import com.example.nromantsov.imageviewer.presenter.interfaces.IPresenterAbout;
import com.example.nromantsov.imageviewer.view.interfaces.IViewAbout;

import java.util.List;

public class AboutPresenter implements IPresenterAbout {
    private IViewAbout iViewAbout;

    private DbHandler dbHandler;
    private String tag, url;
    private Boolean flag = false;

    public AboutPresenter(IViewAbout iViewAbout) {
        this.iViewAbout = iViewAbout;
    }

    @Override
    public void setUrlTag(String url, String tag) {
        this.tag = tag;
        this.url = url;
        new DownLoadImage(this).execute(url);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        iViewAbout.loadImage(bitmap);
    }

    @Override
    public List<String> loadUrlsDataBase() {
        dbHandler = new DbHandler(iViewAbout.getContext());
        List<String> urlList = dbHandler.getUrls(tag);

        if (urlList.size() == 0) {
            flag = false;
        } else {
            for (int i = 0; i < urlList.size(); i++) {
                if (url.equals(urlList.get(i))) {
                    flag = true;
                    break;
                }
                flag = false;
            }
        }
        iViewAbout.colorFloatingActionButton(flag);
        return urlList;
    }

    @Override
    public void applySnackBar(View view) {
        if (flag) {
            iViewAbout.showSnackBar(view, "Удалено из избранного :)");
            deleteDataBase();
        } else {
            iViewAbout.showSnackBar(view, "Добавлено в избранное :)");
            addDataBase();
        }
    }

    @Override
    public void cancelSnackBar() {
        if (flag) {
            deleteDataBase();
        } else {
            addDataBase();
        }
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
