package com.example.nromantsov.imageviewer.Presenter;

import android.view.View;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Model.DownLoadImage;
import com.example.nromantsov.imageviewer.Model.UrlBase;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenterAbout;
import com.example.nromantsov.imageviewer.View.Interface.IViewAbout;

import java.util.List;

public class AboutPresenter implements IPresenterAbout {
    private IViewAbout iViewAbout;
    private ImageView imageView;

    private DbHandler dbHandler;
    private String tag, url;
    private Boolean flag = false;

    public AboutPresenter(IViewAbout iViewAbout, ImageView imageView) {
        this.iViewAbout = iViewAbout;
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
        }else {
            iViewAbout.showSnackBar(view, "Добавлено в избранное :)");
            addDataBase();
        }
    }

    @Override
    public void cancelSnackBar() {
        if (flag) {
            deleteDataBase();
        }else {
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
