package com.example.nromantsov.imageviewer.View;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.nromantsov.imageviewer.Model.ObserverChange;
import com.facebook.stetho.Stetho;

public class ApplicationBase extends android.app.Application {
    public static Obs obs;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        obs = new Obs();
        context = getApplicationContext();
    }

    public static class Obs
    {
        private  ObserverChange observerChange;
        public Obs()
        {
            observerChange = new ObserverChange();
        }

        public ObserverChange getObserverChange() {
            return observerChange;
        }
    }

    public static Context getContext() {
        return context;
    }
}
