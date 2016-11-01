package com.example.nromantsov.imageviewer.DataBase;

public class UrlBase {

    int id;
    String tag;
    String url;

    public UrlBase() {
    }

    public UrlBase(String tag, String url) {
        this.tag = tag;
        this.url = url;
    }

    public UrlBase(int id, String tag, String url) {
        this.id = id;
        this.tag = tag;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
