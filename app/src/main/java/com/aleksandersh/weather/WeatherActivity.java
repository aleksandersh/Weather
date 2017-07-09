package com.aleksandersh.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class WeatherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Получение и установка Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Toggle обеспечивает совместную работу DrawerLayout с ActionBar
        mToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(mToggle);

        // Установка активности обработчиком выбора Navigation view.
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            // При запуске приложения необходимо загрузить стандартный фрагмент
            MenuItem item = navigationView.getMenu().findItem(R.id.nav_weather_fragment);
            if (item != null) {
                onNavigationItemSelected(item);
                item.setChecked(true);
            } else {
                throw new RuntimeException("Can not find primary menu item.");
            }
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
        // Если при нажатии кнопки Back открыт Navigation drawer, вместо закрытия активности
        // закрывается панель.
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_weather_fragment:
                fragment = WeatherFragment.newInstance();
                break;
            case R.id.nav_about_fragment:
                fragment = AboutFragment.newInstance();
                break;
            case R.id.nav_settings_fragment:
                fragment = SettingsFragment.newInstance();
                break;
            default:
                fragment = WeatherFragment.newInstance();
        }

        // Переключение фрагмента
        replaceFragment(fragment);

        // Закрытие панели
        mDrawer.closeDrawer(GravityCompat.START);

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
                mDrawer,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    /**
     * Заменяет фрагмент в контейнере.
     *
     * @param fragment Новый фрагмент
     */
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
