package com.example.nromantsov.imageviewer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.AsyncTask.DownLoadImage;
import com.example.nromantsov.imageviewer.DataBase.DbHandler;
import com.example.nromantsov.imageviewer.DataBase.UrlBase;
import com.example.nromantsov.imageviewer.R;

public class FragmentAbout extends Fragment {

    Snackbar snackbar;
    String url, tag;
    Boolean nameUrl = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgAbout);

        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            tag = bundle.getString("tag");
            imageView.setTag(url);
            new DownLoadImage(imageView).execute(url);
        }

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbHandler = new DbHandler(getActivity());

                if (nameUrl) {
                    snackbar = Snackbar.make(view, "Картинка уже добавлена в избранное :)", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    snackbar = Snackbar.make(view, "Картинка добавлена в избранное :)", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dbHandler.addUrl(new UrlBase(tag, url));
                }

            }
        });

        return v;
    }
}
