package com.example.nromantsov.imageviewer;

import android.os.Bundle;
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

import com.example.nromantsov.imageviewer.DataBase.DbHandler;
import com.example.nromantsov.imageviewer.Fragment.FragmentPagerAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;
    ViewPager viewPager;
    String tagEng = "weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.fragment_pager);

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tagEng));
        tabs.setupWithViewPager(viewPager);
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
                        dbHandler.deleteUrls(tagEng);
                        Toast.makeText(this, "Delete Base", Toast.LENGTH_SHORT).show();
                        break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.cars:
                tagEng = "car";
                break;
            case R.id.robots:
                tagEng = "robots";
                break;
            case R.id.flights:
                tagEng = "aircraft";
                break;
            case R.id.trains:
                tagEng = "trains";
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tagEng));
        return true;
    }
}
