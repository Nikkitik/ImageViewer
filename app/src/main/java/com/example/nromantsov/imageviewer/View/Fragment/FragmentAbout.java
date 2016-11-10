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

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Presenter.AboutPresenter;
import com.example.nromantsov.imageviewer.View.Interface.IViewAbout;
import com.example.nromantsov.imageviewer.View.MainActivity;
import com.example.nromantsov.imageviewer.R;

public class FragmentAbout extends Fragment implements IViewAbout {
    AboutPresenter aboutPresenter;
    FloatingActionButton fab;
    Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgAbout);
        setHasOptionsMenu(true);

        aboutPresenter = new AboutPresenter(this, getActivity(), imageView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Подробно");
        ((MainActivity) getActivity()).setDrawerIndicatorEnabled(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            aboutPresenter.setUrlTag(bundle.getString("url"), bundle.getString("tag"));
        }

        aboutPresenter.loadUrlsDataBase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar(view);
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Мои картинки");
        super.onDestroy();
    }

    private static final String TAG = "FragmentAbout";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorFloatingActionButton(Boolean result) {
        if (result)
            fab.setImageResource(R.drawable.star_black);
        else
            fab.setImageResource(R.drawable.star_white);
    }

    @Override
    public void showSnackBar(View view) {
        Boolean flag = aboutPresenter.getFlag();
        if (flag) {
            snackbar = Snackbar.make(view, "Удалено из избранного :)", Snackbar.LENGTH_LONG)
                    .setAction("Отмена", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            aboutPresenter.addDataBase();
                        }
                    });
            snackbar.show();
            aboutPresenter.deleteDataBase();
        } else {
            snackbar = Snackbar.make(view, "Добавлено в избранное :)", Snackbar.LENGTH_LONG)
                    .setAction("Отмена", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            aboutPresenter.deleteDataBase();
                        }
                    });
            snackbar.show();
            aboutPresenter.addDataBase();
        }
    }
}

