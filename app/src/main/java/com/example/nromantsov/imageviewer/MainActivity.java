package com.example.nromantsov.imageviewer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nromantsov.imageviewer.Fragment.FragmentPagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    ViewPager viewPager;
    List<String> listItem = new ArrayList<>();
    String tagEng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItem.add("Машины");
        listItem.add("Роботы");
        listItem.add("Флаги");

        ListView drawerListView = (ListView) findViewById(R.id.left_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // подключим адаптер для списка
        drawerListView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, R.id.text1, listItem));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        setSupportActionBar(toolbar);

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
        File pictureDirectory = new File("data/data/com.example.nromantsov.imageviewer/");

        for (File f : pictureDirectory.listFiles()) {
            f.delete();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String tag = listItem.get(position);

            switch (tag) {
                case "Машины":
                    tagEng = "car";
                    break;
                case "Роботы":
                    tagEng = "robots";
                    break;
                case "Флаги":
                    tagEng = "flags";
                    break;
            }

            viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, tagEng));
        }
    }
}
