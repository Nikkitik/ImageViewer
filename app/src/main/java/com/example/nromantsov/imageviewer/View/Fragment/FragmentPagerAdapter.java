package com.example.nromantsov.imageviewer.View.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
//            case 1:
//                FragmentFavorite fragmentFavorite = new FragmentFavorite();
//                Bundle bundlefav = new Bundle();
//                bundlefav.putString("tag", tag);
//                fragmentFavorite.setArguments(bundlefav);
//                return fragmentFavorite;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Фото";
                break;
//            case 1:
//                title = "Избранное";
//                break;
        }
        return title;
    }
}
