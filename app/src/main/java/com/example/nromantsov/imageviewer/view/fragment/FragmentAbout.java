package com.example.nromantsov.imageviewer.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.presenter.AboutPresenter;
import com.example.nromantsov.imageviewer.view.interfaces.IViewAbout;
import com.example.nromantsov.imageviewer.view.MainActivity;
import com.example.nromantsov.imageviewer.R;

public class FragmentAbout extends Fragment implements IViewAbout {
    private final static String CANCEL_TEXT = "Отмена";
    private AboutPresenter aboutPresenter;
    private FloatingActionButton fab;
    private ImageView imageView;
    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        fab = v.findViewById(R.id.fab);
        imageView = v.findViewById(R.id.imgAbout);
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

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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

