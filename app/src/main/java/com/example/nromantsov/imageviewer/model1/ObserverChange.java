package com.example.nromantsov.imageviewer.model1;

import java.util.Observable;

public class ObserverChange extends Observable {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setChanged();
        notifyObservers();
    }
}
