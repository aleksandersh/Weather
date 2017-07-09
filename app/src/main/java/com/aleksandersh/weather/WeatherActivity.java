package com.aleksandersh.weather;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class WeatherActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Получение и установка Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Toggle обеспечивает совместную работу DrawerLayout с ActionBar
        mToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawer.addDrawerListener(mToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Синхронизация индикатора с состоянием DrawerLayout
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Enter your code there
        return super.onOptionsItemSelected(item);
    }
}
