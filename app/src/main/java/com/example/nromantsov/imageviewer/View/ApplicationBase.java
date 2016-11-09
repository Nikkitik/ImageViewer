package com.example.nromantsov.imageviewer.View;

import com.example.nromantsov.imageviewer.DataBase.ObserverChange;
import com.facebook.stetho.Stetho;

public class ApplicationBase extends android.app.Application {
    public static Obs obs;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        obs = new Obs();
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
}
