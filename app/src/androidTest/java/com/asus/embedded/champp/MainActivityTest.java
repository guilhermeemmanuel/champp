package com.asus.embedded.champp;

import android.os.RemoteException;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

/**
 * Created by laerciovitorino on 6/23/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    UiDevice mDevice;
    UiObject mMyChamppApp;
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

    public void testFirstScreenBehavior() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        onView(withId(R.id.new_champ_bt)).perform(click());
        onView(withId(R.id.select_modal_tv)).check(matches(isDisplayed()));

        mDevice.pressHome();
        mAppTrayButton = mDevice.findObject(new UiSelector().description("Apps"));
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChamppApp = mDevice.findObject(new UiSelector().description("My Champp"));
        mMyChamppApp.clickAndWaitForNewWindow();
        sleep(1000);

        onView(withId(R.id.select_modal_tv)).perform(pressBack());
        onView(withId(R.id.welcome_tv)).check(matches(isDisplayed()));

        mDevice.pressBack();
        mDevice.pressHome();
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChamppApp.clickAndWaitForNewWindow();
        sleep(5000);

        onView(withId(R.id.welcome_tv)).check(matches(isDisplayed()));

        mDevice.pressHome();
        mAppTrayButton.clickAndWaitForNewWindow();
        mMyChamppApp.clickAndWaitForNewWindow();
        sleep(1000);

        onView(withId(R.id.welcome_tv)).check(matches(isDisplayed()));
    }
}
