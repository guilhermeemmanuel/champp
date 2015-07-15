package com.asus.embedded.champp;

import android.os.RemoteException;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

/**
 * Created by laerciovitorino on 6/23/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    UiDevice mDevice;
    UiObject mMyChampApp;
    UiObject mAppTrayButton;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    public void testAppCompleteBehavior() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        // TODO
        mDevice.pressHome();
        mAppTrayButton = mDevice.findObject(new UiSelector().description("Apps"));
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChampApp = mDevice.findObject(new UiSelector().description("My Champ"));
        mMyChampApp.clickAndWaitForNewWindow();
        sleep(1000);

        // TODO
        onView(withId(R.id.welcome_tv)).check(matches(isDisplayed()));

        // TODO
        mDevice.pressBack();
        mDevice.pressHome();
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChampApp = mDevice.findObject(new UiSelector().description("My Champ"));
        mMyChampApp.clickAndWaitForNewWindow();
        sleep(1000);

        // Screen displayed just after the application has been started
        onView(withId(R.id.new_champ_bt)).perform(click());
        onView(withId(R.id.select_modal_tv)).check(matches(isDisplayed()));

        // Simulate the behavior of putting the application running in background and then bring it back to active state
        mDevice.pressHome();
        mAppTrayButton = mDevice.findObject(new UiSelector().description("Apps"));
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChampApp = mDevice.findObject(new UiSelector().description("My Champ"));
        mMyChampApp.clickAndWaitForNewWindow();
        sleep(1000);

        // TODO
        onView(withId(R.id.select_modal_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.select_modal_tv)).perform(pressBack());
        onView(withId(R.id.welcome_tv)).check(matches(isDisplayed()));

        // TODO
        onView(withId(R.id.new_champ_bt)).perform(click());
        onView(withId(R.id.select_modal_tv)).check(matches(isDisplayed()));

        // TODO
        // TODO
        onView(withId(R.id.football_ib)).perform(click());

        getActivity();

        // TODO
        onView(withId(R.id.new_champ_name_et)).check(matches(isDisplayed()));
        onView(withText(R.string.ok)).perform(click());
        onView(withId(R.id.new_champ_name_et)).check(matches(isDisplayed()));

        // TODO
        onView(withId(R.id.radio_champ_league)).perform(click());
        onView(withId(R.id.radio_champ_cup)).perform(click());

        // TODO
        onView(withId(R.id.new_champ_name_et)).perform(typeText("   "));
        onView(withText(R.string.ok)).perform(click());
        onView(withId(R.id.new_champ_name_et)).check(matches(isDisplayed()));

        // TODO
        onView(withId(R.id.new_champ_name_et)).perform(clearText());
        onView(withId(R.id.new_champ_name_et)).perform(typeText("Liga"));
        onView(withId(R.id.radio_champ_league)).perform(click());
        onView(withText(R.string.ok)).perform(click());

        // TODO
        onView(withId(R.id.league_settings_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_champ_return)).perform(click());
        onView(withId(R.id.radio_champ_turn)).perform(click());
        mDevice.pressBack();

        // TODO
        onView(withId(R.id.new_champ_name_et)).perform(clearText());
        onView(withId(R.id.new_champ_name_et)).perform(typeText("Copa"));
        onView(withId(R.id.radio_champ_cup)).perform(click());
        onView(withText(R.string.ok)).perform(click());

        // TODO
        onView(withId(R.id.cup_settings_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.cup_set_home_win)).perform(click());
        onView(withId(R.id.cup_set_penalities)).perform(click());

        // TODO
        mDevice.pressHome();
        mAppTrayButton = mDevice.findObject(new UiSelector().description("Apps"));
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChampApp = mDevice.findObject(new UiSelector().description("My Champ"));
        mMyChampApp.clickAndWaitForNewWindow();
        sleep(1000);

        // TODO
        onView(withText(R.string.ok)).perform(click());
    }
}
