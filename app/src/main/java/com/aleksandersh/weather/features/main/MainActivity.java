package com.aleksandersh.weather.features.main;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.about.AboutFragment;
import com.aleksandersh.weather.features.settings.SettingsFragment;
import com.aleksandersh.weather.features.weather.presentation.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "WeatherActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        // Toggle обеспечивает совместную работу DrawerLayout с ActionBar
        mToggle = setupDrawerToggle();
        drawer.addDrawerListener(mToggle);

        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
            // При запуске приложения необходимо загрузить стандартный фрагмент
            selectNavigationMenuItem(R.id.nav_weather_fragment);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Синхронизация индикатора с состоянием DrawerLayout
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Новая конфигурация передается Toggle, хотя я не совсем понял зачем это здесь
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_weather_fragment:
                fragment = WeatherFragment.newInstance();
                break;
            case R.id.nav_settings_fragment:
                fragment = SettingsFragment.newInstance();
                break;
            case R.id.nav_about_fragment:
                fragment = AboutFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("Menu item Id did not processed.");
        }

        replaceFragment(fragment);
        // Установка нового заголовка, для начала хватит названия пункта меню
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * Собирает новый экземпляр {@link ActionBarDrawerToggle}.
     *
     * @return Новый экземпляр.
     */
    protected ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(
                this,
                drawer,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    /**
     * Заменяет фрагмент в контейнере.
     *
     * @param fragment Новый фрагмент.
     */
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    /**
     * Переключает пункт в панели навигации.
     *
     * @param itemId Id ресурса выбираемого пункта меню.
     * @throws IllegalArgumentException Исключение возникает, если пункт с таким Id не найден .
     */
    private void selectNavigationMenuItem(@IdRes int itemId) {
        MenuItem item = mNavigationView.getMenu().findItem(itemId);
        if (item != null) {
            onNavigationItemSelected(item);
            item.setChecked(true);
        } else {
            throw new IllegalArgumentException("Can not find menu item by id.");
        }
    }

}