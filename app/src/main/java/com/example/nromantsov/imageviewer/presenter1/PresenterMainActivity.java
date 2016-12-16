package com.example.nromantsov.imageviewer.presenter1;

import android.widget.Toast;

import com.example.nromantsov.imageviewer.model1.DbHandler;
import com.example.nromantsov.imageviewer.presenter1.interfaces.IPresenterMainActivity;
import com.example.nromantsov.imageviewer.view1.fragment1.DialogFragmentSearch;
import com.example.nromantsov.imageviewer.view1.MainActivity;

import java.io.File;

public class PresenterMainActivity implements IPresenterMainActivity {

    private MainActivity mainActivity;

    public PresenterMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void deleteFileFromFolder() {
        File pictureDirectory = new File("data/data/com.example.nromantsov.imageviewer/");

        for (File f : pictureDirectory.listFiles()) {
            f.delete();
        }

        Toast.makeText(mainActivity, "Folder is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFileFromDataBase(String tag) {
        DbHandler dbHandler = new DbHandler(mainActivity);
        dbHandler.deleteUrls(tag);
        Toast.makeText(mainActivity, "Delete Base", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteAllFileFromDataBase() {
        DbHandler dbHandler = new DbHandler(mainActivity);
        dbHandler.deleteUrlFavoriteAll();
        Toast.makeText(mainActivity, "Delete Base All", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createDialogSearch() {
        DialogFragmentSearch dialogFragmentSearch = new DialogFragmentSearch(this);
        dialogFragmentSearch.show(mainActivity.getSupportFragmentManager(), "search");
    }

    @Override
    public void createFavoriteAll() {
        mainActivity.loadFragmentFavorite();
    }

    @Override
    public void setTagDialogSearch(String tag) {
        mainActivity.onTag(tag);
    }
}
