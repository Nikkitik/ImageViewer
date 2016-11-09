package com.example.nromantsov.imageviewer.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nromantsov.imageviewer.Fragment.FragmentFavorite;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String tag;

    public FragmentPagerAdapter(FragmentManager fm, String tag) {
        super(fm);
        this.tag = tag;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                FragmentFavorite fragmentFavorite = new FragmentFavorite();
                Bundle bundlefav = new Bundle();
                bundlefav.putString("tag", tag);
                fragmentFavorite.setArguments(bundlefav);
                return fragmentFavorite;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Фото";
                break;
            case 1:
                title = "Избранное";
                break;
        }
        return title;
    }
}