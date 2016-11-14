package com.example.nromantsov.imageviewer.Presenter.Interface;

public interface IPresenterMainActivity {
    void deleteFileFromFolder();
    void deleteFileFromDataBase(String tag);
    void deleteAllFileFromDataBase();
    void createDialogSearch();
    void createFavoriteAll();

    void setTagDialogSearch(String tag);
}
