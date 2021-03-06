package com.example.nromantsov.imageviewer.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                MainFragment mainFragment = new MainFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tag", tag);
                bundle.putString("fragment", "main");
                mainFragment.setArguments(bundle);
                return mainFragment;
            case 1:
                MainFragment fragmentFavorite = new MainFragment();
                bundle = new Bundle();
                bundle.putString("tag", tag);
                bundle.putString("fragment", "favorite");
                fragmentFavorite.setArguments(bundle);
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
