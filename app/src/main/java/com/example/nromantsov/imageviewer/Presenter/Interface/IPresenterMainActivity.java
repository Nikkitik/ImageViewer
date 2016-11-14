package com.example.nromantsov.imageviewer.Presenter.Interface;

public interface IPresenterMainActivity {
    void deleteFileFromFolder();
    void deleteFileFromDataBase(String tag);
    void createDialogSearch();

    void setTagDialogSearch(String tag);
}
