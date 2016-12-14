package com.example.nromantsov.imageviewer.view.adapter;

import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nromantsov.imageviewer.model.DownLoadImage;
import com.example.nromantsov.imageviewer.presenter.interfaces.IPresenterMain;
import com.example.nromantsov.imageviewer.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageHolder>{

    private List<String> sourceList;
    private int screenHeight, screenWidth;

    private IPresenterMain iPresenter;

    static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imgView;

        ImageHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }

    public RecyclerAdapter(List<String> sourceList, int screenHeight, int screenWidth, IPresenterMain iPresenter) {
        this.sourceList = sourceList;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.iPresenter = iPresenter;
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

        if (iPresenter != null) {
            if (iPresenter.getFragmentName().equals("main")) {
                if (position >= getItemCount() - 1) {
                    iPresenter.loadData();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return sourceList != null ? sourceList.size() : 0;
    }
}
