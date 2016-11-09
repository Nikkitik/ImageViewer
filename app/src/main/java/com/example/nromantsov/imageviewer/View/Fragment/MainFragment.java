package com.example.nromantsov.imageviewer.View.Fragment;

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

import com.example.nromantsov.imageviewer.Fragment.FragmentAbout;
import com.example.nromantsov.imageviewer.Model.ParserJSON;
import com.example.nromantsov.imageviewer.Interface.ISourceArray;
import com.example.nromantsov.imageviewer.Presenter.UrlListPresenter;
import com.example.nromantsov.imageviewer.R;
import com.example.nromantsov.imageviewer.View.Adapter.ItemClickSupport;
import com.example.nromantsov.imageviewer.View.Adapter.RecyclerAdapter;
import com.example.nromantsov.imageviewer.View.Interface.IView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements IView {
    List<String> sourceList = new ArrayList<>();
    RecyclerAdapter adapter = null;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        UrlListPresenter presenter = new UrlListPresenter(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        hideProgressbar();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvImage);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }

        recyclerView.setLayoutManager(layoutManager);

        if (adapter == null) {
            presenter.setTag("");
            presenter.getUrl();
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth, presenter);
        } else {
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth, presenter);
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                FragmentAbout fragmentAbout = new FragmentAbout();
                Bundle bundle = new Bundle();

                bundle.putString("url", sourceList.get(position));
//                bundle.putString("tag", tag);

                fragmentAbout.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fl, fragmentAbout, "about")
                        .addToBackStack(null)
                        .commit();
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
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }
}