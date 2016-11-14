package com.example.nromantsov.imageviewer.View.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.Presenter.AboutPresenter;
import com.example.nromantsov.imageviewer.View.Interface.IViewAbout;
import com.example.nromantsov.imageviewer.View.MainActivity;
import com.example.nromantsov.imageviewer.R;

public class FragmentAbout extends Fragment implements IViewAbout {
    private final static String CANCEL_TEXT = "Отмена";
    AboutPresenter aboutPresenter;
    FloatingActionButton fab;
    ImageView imageView;
    Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        imageView = (ImageView) v.findViewById(R.id.imgAbout);
        setHasOptionsMenu(true);

        aboutPresenter = new AboutPresenter(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            aboutPresenter.setUrlTag(bundle.getString("url"), bundle.getString("tag"));
        }

        aboutPresenter.loadUrlsDataBase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutPresenter.applySnackBar(view);
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Подробно");
        ((MainActivity) getActivity()).setDrawerIndicatorEnabled(false);
    }

    @Override
    public void colorFloatingActionButton(Boolean result) {
        if (result)
            fab.setImageResource(R.drawable.star_black);
        else
            fab.setImageResource(R.drawable.star_white);
    }

    @Override
    public void showSnackBar(View view, String msg) {
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction(CANCEL_TEXT, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aboutPresenter.cancelSnackBar();
                    }
                });
        snackbar.show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void loadImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}

