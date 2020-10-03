package com.group11.rentacaradmin;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class IT19150130 {

    @Rule
    public ActivityTestRule<AdminHomeActivity> adminHomeActivityActivityTestRule = new ActivityTestRule<AdminHomeActivity>(AdminHomeActivity.class);
    private AdminHomeActivity adminHomeActivity = null;

    @Before
    public void setUp() throws Exception {
        adminHomeActivity = adminHomeActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = adminHomeActivity.findViewById(R.id.recyclerview);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        adminHomeActivity = null;
    }

  /*  @Rule
    public ActivityTestRule<AddVehicleActivity> addVehicleActivityActivityTestRule = new ActivityTestRule<AddVehicleActivity>(AddVehicleActivity.class);
    private AddVehicleActivity addVehicleActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AdminHomeActivity.class.getName(),null,false);

    @Before
    public void setUp1() throws Exception{
        addVehicleActivity = addVehicleActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfAdminHomeActivityOnCancelButtonClick(){
        assertNotNull(addVehicleActivity.findViewById(R.id.cancelAddBtn));
        onView(withId(R.id.cancelAddBtn)).perform(click());
        Activity adHomeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(adHomeActivity);
        adHomeActivity.finish();
    }

    @After
    public void tearDown1() throws Exception{
        addVehicleActivity = null;
    } */

    @Rule
    public ActivityTestRule<AddVehicleActivity> addVehicleActivityActivityTestRule1 = new ActivityTestRule<AddVehicleActivity>(AddVehicleActivity.class);
    private AddVehicleActivity addVehicleActivity1 = null;

    @Before
    public void setUp2() throws Exception {
        addVehicleActivity1 = addVehicleActivityActivityTestRule1.getActivity();
    }

    @Test
    public void testLaunch2(){
        View view = addVehicleActivity1.findViewById(R.id.image);
        assertNotNull(view);
    }

    @After
    public void tearDown2() throws Exception {
        addVehicleActivity1 = null;
    }







 /*   @Rule
    public ActivityTestRule<EditActivity> editActivityActivityTestRule = new ActivityTestRule<EditActivity>(EditActivity.class);
    private EditActivity editActivity = null;

    @Before
    public void setUp3() throws Exception {
        editActivity = editActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch3(){
        View view = editActivity.findViewById(R.id.textView1);
        assertNotNull(view);
    }

    @After
    public void tearDown3() throws Exception {
        editActivity = null;
    }
*/

}