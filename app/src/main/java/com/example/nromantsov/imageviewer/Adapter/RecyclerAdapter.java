package com.example.nromantsov.imageviewer.Adapter;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.AsyncTask.DownLoadImage;
import com.example.nromantsov.imageviewer.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageHolder> {

    private List<String> sourceList;
    private LoadData loadData;
    private int screenHeight, screenWidth;

    public interface LoadData {
        void load();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imgView;

        ImageHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }

    public RecyclerAdapter(List<String> sourceList, int screenHeight, int screenWidth) {
        this.sourceList = sourceList;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        if (v.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            v.getLayoutParams().height = screenHeight / 4;
            v.getLayoutParams().width = screenWidth / 2;
        } else {
            v.getLayoutParams().height = screenHeight / 2;
            v.getLayoutParams().width = screenWidth / 3;
        }

        return new ImageHolder(v);
    }


    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        if (holder.imgView.getTag() != null && !holder.imgView.getTag().equals(sourceList.get(position)))
            holder.imgView.setImageBitmap(null);
        holder.imgView.setTag(sourceList.get(position));
        new DownLoadImage(holder.imgView).executeOnExecutor(DownLoadImage.THREAD_POOL_EXECUTOR, sourceList.get(position));

        if (loadData != null) {
            if (position >= getItemCount() - 1) {
                loadData.load();
            }
        }
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
    }
}
