package com.aleksandersh.weather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.aleksandersh.weather.di.component.AppComponent;
import com.aleksandersh.weather.features.main.MainActivity;
import com.aleksandersh.weather.utils.Const;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by Vladimir Kondenko on 28.07.17.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WeatherTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class);

    private static OkHttp3IdlingResource idlingResource;

    @BeforeClass
    public static void setUpClass() {
        AppComponent appComponent = App.getAppComponent();
        OkHttpClient client = appComponent.getWeatherHttpClient();
        idlingResource = OkHttp3IdlingResource.create(Const.OKHTTP_IDLING_RESOURCE_NAME, client);
    }

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_weather_fragment));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void shouldOpenCityFinder() {
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplaySuggestionEng() {
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
        onView(withId(R.id.citychooser_textview_search_city)).perform(typeText("Moscow"));
        onView(withChild(withText("Moscow"))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplaySuggestionRu() {
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
        onView(withId(R.id.citychooser_textview_search_city)).perform(replaceText("Москва"));
        onView(withChild(withText("Москва"))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldChangeCity() {
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
        onView(withId(R.id.citychooser_textview_search_city)).perform(typeText("Taganrog"));
        onView(withText("Taganrog")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        onView(withText("Taganrog")).check(matches(isDisplayed()));
        onView(withText("Moscow")).check(doesNotExist());
    }

    @Test
    public void shouldSaveCity() {
        String city = "Taganrog";
        onView(withId(R.id.action_weather_toolbar_change_city)).perform(click());
        onView(withChild(withId(R.id.fragment_city_chooser_root))).check(matches(isDisplayed()));
        onView(withId(R.id.citychooser_textview_search_city)).perform(replaceText(city));
        onView(withText(city)).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(activityTestRule.getActivity());
        String currentCityNameKey = activityTestRule.getActivity().getString(R.string.pref_current_city_name);
        String savedName = preferenceManager.getString(currentCityNameKey, null);
        assertThat(savedName, notNullValue());
        assertEquals(city, savedName);
    }   


}
