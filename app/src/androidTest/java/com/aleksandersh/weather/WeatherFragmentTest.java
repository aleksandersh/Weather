package com.aleksandersh.weather;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Vladimir Kondenko on 28.07.17.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WeatherFragmentTest {

    @Rule
    public ActivityTestRule<WeatherActivity> activityTestRule = new ActivityTestRule(WeatherActivity.class);

    @Before // Class
    public void setUp() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_weather_fragment));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
    }

    @Test
    public void shouldOpenCityFinder() {
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplaySuggestionEng() {

    }

    @Test
    public void shouldDisplaySuggestionRu() {

    }

    @Test
    public void shouldSaveCity() {

    }

    @Test
    public void shouldChangeCity() {

    }


}
