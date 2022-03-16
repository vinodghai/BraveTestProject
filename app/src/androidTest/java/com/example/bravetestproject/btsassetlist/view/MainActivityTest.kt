package com.example.bravetestproject.btsassetlist.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.bravetestproject.R
import com.example.bravetestproject.btcassetlist.view.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun validateInitialStateOfUI() {
        onView(withText("Crypto")).check(matches(isDisplayed()))
        onView(withText("Assets")).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressCircular)).check(matches(isDisplayed()))
        onView(withId(R.id.ivSearch)).check(matches(not(isDisplayed())))
        onView(withId(R.id.ivMenu)).check(matches(not(isDisplayed())))
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.rvBtcAssetList)).check(matches(not(isDisplayed())))
        onView(withId(R.id.etSearch)).check(matches(not(isDisplayed())))
    }
}