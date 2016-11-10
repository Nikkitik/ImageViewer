package com.example.nromantsov.imageviewer.View;

//https://github.com/KKorvin/TinyStockQuotes

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nromantsov.imageviewer.Model.DbHandler;
import com.example.nromantsov.imageviewer.Fragment.DialogFragmentSearch;
import com.example.nromantsov.imageviewer.R;
import com.example.nromantsov.imageviewer.View.Fragment.FragmentPagerAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogFragmentSearch.OnTagDialog {
    FragmentManager fragmentManager;
    ViewPager viewPager;
    ActionBarDrawerToggle toggle;

    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                switch (viewPage) {
                    case 0:
                        File pictureDirectory = new File("data/data/com.example.nromantsov.imageviewer/");

                        for (File f : pictureDirectory.listFiles()) {
                            f.delete();
                        }

                        Toast.makeText(this, "Folder is empty", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        DbHandler dbHandler = new DbHandler(this);
//                        dbHandler.deleteUrls(tagEng);
                        Toast.makeText(this, "Delete Base", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.action_search:
                DialogFragmentSearch dialogFragmentSearch = new DialogFragmentSearch();
                dialogFragmentSearch.show(getSupportFragmentManager(), "search");
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tag));
        return true;
    }

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
}
