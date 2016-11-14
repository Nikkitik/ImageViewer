package com.example.nromantsov.imageviewer.Presenter;

import android.widget.Toast;

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Presenter.Interface.IPresenterMainActivity;
import com.example.nromantsov.imageviewer.View.Fragment.DialogFragmentSearch;
import com.example.nromantsov.imageviewer.View.MainActivity;

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
    public void createDialogSearch() {
        DialogFragmentSearch dialogFragmentSearch = new DialogFragmentSearch(this);
        dialogFragmentSearch.show(mainActivity.getSupportFragmentManager(), "search");
    }

    @Override
    public void setTagDialogSearch(String tag) {
        mainActivity.onTag(tag);
    }
}
