package com.example.nromantsov.imageviewer.view;

//https://github.com/KKorvin/TinyStockQuotes

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.nromantsov.imageviewer.presenter.PresenterMainActivity;
import com.example.nromantsov.imageviewer.R;
import com.example.nromantsov.imageviewer.view.fragment.FragmentPagerAdapter;
import com.example.nromantsov.imageviewer.view.fragment.MainFragment;
import com.example.nromantsov.imageviewer.view.interfaces.IViewMainActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IViewMainActivity {
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private ActionBarDrawerToggle toggle;
    private PresenterMainActivity presenterMainActivity;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenterMainActivity = new PresenterMainActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        toggle.syncState();
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        fragmentManager = getSupportFragmentManager();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.fragment_pager);

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tag));
        tabs.setupWithViewPager(viewPager);
        setDrawerIndicatorEnabled(true);
    }

    public void setDrawerIndicatorEnabled(boolean set) {
        this.toggle.setDrawerIndicatorEnabled(set);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (!toggle.onOptionsItemSelected(item)) onBackPressed();
                break;
            case R.id.action_delete:
                int viewPage = viewPager.getCurrentItem();
                if (fragmentManager.findFragmentByTag("favorite") != null)
                    presenterMainActivity.deleteAllFileFromDataBase();
                else {
                    switch (viewPage) {
                        case 0:
                            presenterMainActivity.deleteFileFromFolder();
                            break;
                        case 1:
                            presenterMainActivity.deleteFileFromDataBase(tag);
                            break;
                    }
                }
                break;
            case R.id.action_search:
                presenterMainActivity.createDialogSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager.getBackStackEntryCount() == 0) {
                if (viewPager.getCurrentItem() == 1)
                    viewPager.setCurrentItem(0);
                else
                    super.onBackPressed();
            } else {
                super.onBackPressed();
                viewPager.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cars:
                removeAbout();
                tag = "car";
                break;
            case R.id.robots:
                removeAbout();
                tag = "robots";
                break;
            case R.id.flights:
                removeAbout();
                tag = "aircraft";
                break;
            case R.id.trains:
                removeAbout();
                tag = "trains";
                break;
            case R.id.all_favorite:
                presenterMainActivity.createFavoriteAll();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tag));
        return true;
    }

    @Override
    public void removeAbout() {
        if (fragmentManager.findFragmentByTag("about") != null) {
            fragmentManager.findFragmentByTag("about").onDestroy();
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("about")).commit();
        }
    }

    @Override
    public void onTag(String tag) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tag));
    }

    @Override
    public void loadFragmentFavorite() {
        viewPager.setVisibility(View.GONE);
        MainFragment fragmentFavorite = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment", "favoriteAll");
        fragmentFavorite.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fl, fragmentFavorite, "favorite")
                .addToBackStack(null)
                .commit();
    }
}
