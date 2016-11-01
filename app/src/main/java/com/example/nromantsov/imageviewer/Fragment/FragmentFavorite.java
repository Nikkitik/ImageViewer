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

import com.example.nromantsov.imageviewer.Adapter.ItemClickSupport;
import com.example.nromantsov.imageviewer.Adapter.RecyclerAdapter;
import com.example.nromantsov.imageviewer.DataBase.DbHandler;
import com.example.nromantsov.imageviewer.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {

    RecyclerAdapter adapter = null;
    List<String> urlBase = new ArrayList<>();
    String tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        Bundle bundle = getArguments();

        if (bundle != null)
            tag = getArguments().getString("tag", "weather");

        DbHandler dbHandler = new DbHandler(getActivity());
        urlBase = dbHandler.getUrls(tag);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvImage);

        final RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(urlBase, screenHeight, screenWidth);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                FragmentAbout fragmentAbout = new FragmentAbout();
                Bundle bundle = new Bundle();

                bundle.putString("url", urlBase.get(position));

                fragmentAbout.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.drawer_layout, fragmentAbout)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }
}
