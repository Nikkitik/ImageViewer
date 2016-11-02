package com.example.nromantsov.imageviewer.Fragment;

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

import com.example.nromantsov.imageviewer.Adapter.ItemClickSupport;
import com.example.nromantsov.imageviewer.Adapter.RecyclerAdapter;
import com.example.nromantsov.imageviewer.AsyncTask.ParserJSON;
import com.example.nromantsov.imageviewer.Interface.ISourceArray;
import com.example.nromantsov.imageviewer.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements ISourceArray {
    List<String> sourceList = new ArrayList<>();
    RecyclerAdapter adapter = null;
    ProgressBar progressBar;
    int page = 1;
    String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle bundle = getArguments();

        if (bundle != null)
            tag = getArguments().getString("tag", "weather");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
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
            new ParserJSON(this, progressBar, page, tag).execute();
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth);
        } else {
            progressBar.setVisibility(View.GONE);
            adapter = new RecyclerAdapter(sourceList, screenHeight, screenWidth);
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                FragmentAbout fragmentAbout = new FragmentAbout();
                Bundle bundle = new Bundle();

                bundle.putString("url", sourceList.get(position));
                bundle.putString("tag", tag);

                fragmentAbout.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.drawer_layout, fragmentAbout)
                        .addToBackStack(null)
                        .commit();
            }
        });

        adapter.setLoadData(new RecyclerAdapter.LoadData() {
            @Override
            public void load() {
                loadMore();
            }
        });
        return v;
    }

    private void loadMore() {
        page++;
        new ParserJSON(this, progressBar, page, tag).execute();
    }

    @Override
    public void sourceLoader(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            sourceList.add(strings.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}