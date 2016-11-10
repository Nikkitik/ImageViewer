package com.example.nromantsov.imageviewer.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.Model.DownLoadImage;
import com.example.nromantsov.imageviewer.DataBase.DbHandler;
import com.example.nromantsov.imageviewer.DataBase.UrlBase;
import com.example.nromantsov.imageviewer.View.MainActivity;
import com.example.nromantsov.imageviewer.R;

import java.util.List;

public class FragmentAbout extends Fragment {

    Snackbar snackbar;
    String url, tag;
    Boolean nameUrl = false;

    DbHandler dbHandler;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgAbout);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Подробно");
        ((MainActivity)getActivity()).setDrawerIndicatorEnabled(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            tag = bundle.getString("tag");
            imageView.setTag(url);
            new DownLoadImage(imageView).execute(url);
        }

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        dbHandler = new DbHandler(getActivity());
        List<String> urlList = dbHandler.getUrls(tag);


        for (int i = 0; i < urlList.size(); i++) {
            if (url.equals(urlList.get(i)))
                nameUrl = true;
        }

        if (nameUrl)
            fab.setImageResource(R.drawable.star_black);
        else
            fab.setImageResource(R.drawable.star_white);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameUrl) {
                    snackbar = Snackbar.make(view, "Удалено из избранного :)", Snackbar.LENGTH_LONG)
                            .setAction("Отмена", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addURLDataBase();
                                }
                            });
                    snackbar.show();

                    deleteURLDataBase();
                } else {
                    snackbar = Snackbar.make(view, "Добавлено в избранное :)", Snackbar.LENGTH_LONG)
                            .setAction("Отмена", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteURLDataBase();
                                }
                            });
                    snackbar.show();

                    addURLDataBase();
                }
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    public void addURLDataBase() {
        dbHandler.addUrl(new UrlBase(tag, url));
        nameUrl = true;
        fab.setImageResource(R.drawable.star_black);
    }

    public void deleteURLDataBase() {
        dbHandler.deleteUrlFavorite(url);
        nameUrl = false;
        fab.setImageResource(R.drawable.star_white);
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Мои картинки");
        super.onDestroy();
    }

    private static final String TAG = "FragmentAbout";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);
    }
}

