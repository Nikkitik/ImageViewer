package com.example.nromantsov.imageviewer.View.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nromantsov.imageviewer.Presenter.UrlListPresenter;
import com.example.nromantsov.imageviewer.R;
import com.example.nromantsov.imageviewer.View.Adapter.ItemClickSupport;
import com.example.nromantsov.imageviewer.View.Adapter.RecyclerAdapter;
import com.example.nromantsov.imageviewer.View.ApplicationBase;
import com.example.nromantsov.imageviewer.View.Interface.IViewMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainFragment extends Fragment implements IViewMain, Observer {
    List<String> sourceList = new ArrayList<>();
    RecyclerAdapter adapter = null;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    UrlListPresenter presenter;

    int screenWidth, screenHeight;
    String tag, name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        presenter = new UrlListPresenter(this);

        ApplicationBase.obs.getObserverChange().addObserver(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
            name = bundle.getString("fragment");
        }

        presenter.setTag(tag);
        presenter.setFragmentName(name);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        hideProgressbar();

        recyclerView = (RecyclerView) v.findViewById(R.id.rvImage);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }

        recyclerView.setLayoutManager(layoutManager);

        if (adapter == null) {
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth, presenter);
            presenter.getUrl();
        } else {
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth, presenter);
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                presenter.setUrlAbout(sourceList.get(position));
            }
        });
        return v;
    }


    @Override
    public void loadUrl(List<String> urls) {
        if (urls != null) {
            for (int i = 0; i < urls.size(); i++) {
                sourceList.add(urls.get(i));
            }

            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public void loadAboutFragment(String url, String tag) {
        FragmentAbout fragmentAbout = new FragmentAbout();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("tag", tag);

        fragmentAbout.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fl, fragmentAbout, "about")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (name.equals("favorite")) {
            sourceList.clear();
            presenter.getUrl();
            adapter.notifyDataSetChanged();
        }
    }
}