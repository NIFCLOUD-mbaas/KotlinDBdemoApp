package ncmbdataquick.mbaas.com.nifcloud.datastorequickstart

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExecuteUITest {

    var btnStart: ViewInteraction? = null

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        btnStart = onView(withId(R.id.btnStart))
    }

    @Test
    fun doStartDemo() {
        btnStart!!.perform(click())
        Thread.sleep(5000)
        onView(withText(containsString("Save successfull! with ID:"))).check(matches(isDisplayed()))
    }

}

