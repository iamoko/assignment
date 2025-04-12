package com.microinvestment

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*


class LoginActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // Optionally perform any setup before tests run, e.g. logging in if needed
    }

    @Test
    fun testLoginFieldsAndButton() {
        // Check that the username EditText exists and is displayed
        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))

        // Check that the password EditText exists and is displayed
        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))

//        // Check that the login button is displayed
//        onView(withId(R.id.loginButton))
//            .check(matches(isDisplayed()))

        // Perform typing actions on the username and password fields
        onView(withId(R.id.usernameEditText))
            .perform(typeText("testUser"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())

        // Perform the login button click
//        onView(withId(R.id.loginButton)).perform(click())

    }

    @Test
    fun testErrorMessageOnLoginFailure() {
        // Check for error message on invalid login
        onView(withId(R.id.usernameEditText))
            .perform(typeText("wrongUser"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("wrongPass"), closeSoftKeyboard())

//        onView(withId(R.id.loginButton)).perform(click())

        // Check that the error message shows up after the invalid login attempt
        onView(withText("Invalid username or password"))
            .check(matches(isDisplayed()))
    }
}