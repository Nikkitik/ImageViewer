package com.example.nromantsov.imageviewer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.AsyncTask.DownLoadImage;
import com.example.nromantsov.imageviewer.R;

public class FragmentAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgAbout);

        Bundle bundle = getArguments();
        if (bundle != null) {
            imageView.setTag(bundle.getString("tag"));
            new DownLoadImage(imageView).execute(bundle.getString("tag"));
        }

        return v;
    }
}
