package com.torres.pruebatecnica

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.torres.pruebatecnica.adapter.MovieAdapter
import com.torres.pruebatecnica.data.model.FakeMovieData
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_IN_TEST = 3
    val MOVIE_IN_TEST = FakeMovieData.movies[LIST_ITEM_IN_TEST]



    @Test
    fun test_isHomeFragmentVisible_onAppLaunch() {
        onView(withId(R.id.rv_upcoming)).check(matches(isDisplayed()))
    }


    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(allOf(withId(R.id.rv_upcoming)))
            .perform(scrollTo())
            .perform(actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(LIST_ITEM_IN_TEST, click()))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(withText(MOVIE_IN_TEST.nameMovie)))
    }

    @Test
    fun test_backNavigation_toHomeFragment() {

        onView(withId(R.id.rv_upcoming))
            .perform(scrollTo())
            .perform(actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(LIST_ITEM_IN_TEST, click()))

        onView(withId(R.id.tv_title)).check(matches(withText(MOVIE_IN_TEST.nameMovie)))

        pressBack()

        onView(withId(R.id.rv_upcoming)).check(matches(not(isDisplayed())))
    }

}