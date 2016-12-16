package com.example.nromantsov.imageviewer.model;

import android.os.AsyncTask;

import com.example.nromantsov.imageviewer.model.interfaces.IModel;
import com.example.nromantsov.imageviewer.presenter.interfaces.IPresenterMain;

import java.util.ArrayList;
import java.util.List;

import static com.example.nromantsov.imageviewer.model.JSONUtils.connectionURL;

public class ParserJSON extends AsyncTask<String, Integer, Boolean> {
    private List<String> sourceArray = new ArrayList<>();

    private IModel iModel;
    private IPresenterMain iPresenter;

    public ParserJSON(IModel iModel, IPresenterMain iPresenter) {
        this.iModel = iModel;
        this.iPresenter = iPresenter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        sourceArray = JSONUtils.getSourceArray(connectionURL(iPresenter.getTag(), iPresenter.getPage()));
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (!isCancelled()) {
            if (result)
                iModel.listUrl(sourceArray);
        }
    }
}