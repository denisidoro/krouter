package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.github.denisidoro.krouter.Schema.Type.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
@RunWith(RobolectricGradleTestRunner::class)
class RouterTest {

    @Mock
    lateinit var context: Context
    @Mock
    lateinit var activity: Activity

    lateinit var router: Router

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testInferRegex() {
        givenDefaultRouter()

        assertEquals(INT, router.inferRegex("3"))
        assertEquals(INT, router.inferRegex("345"))
        assertEquals(INT, router.inferRegex("-345"))
        assertEquals(INT, router.inferRegex("0"))
        assertEquals(FLOAT, router.inferRegex("42.0"))
        assertEquals(FLOAT, router.inferRegex("-43.123"))
        assertEquals(FLOAT, router.inferRegex("43.123f"))
        assertEquals(DOUBLE, router.inferRegex("43.123d"))
        assertEquals(LONG, router.inferRegex("43123L"))
        assertEquals(STRING, router.inferRegex("j"))
        assertEquals(STRING, router.inferRegex("john"))
    }

    @Test
    fun testIntent_OneInt() {
        router = Router("user/42/profile", Route("user/:id/profile"), Activity::class.java, context)
        assertEquals(42, router.intent.getIntExtra("id", 0))
    }

    @Test
    fun testIntent_TwoInt() {
        router = Router("user/42/profile/page/2", Route("user/:id/profile/page/:pid"), Activity::class.java, context)
        assertEquals(42, router.intent.getIntExtra("id", 0))
        assertEquals(2, router.intent.getIntExtra("pid", 0))
    }

    @Test
    fun testIntent_OneString_OneFloat() {
        router = Router("user/john/balance/42.75", Route("user/:name/balance/:balance"), Activity::class.java, context)
        assertEquals("john", router.intent.getStringExtra("name"))
        assertEquals(42.75f, router.intent.getFloatExtra("balance", 0f))
    }

    @Test
    fun testIntent_OneFloatWithF() {
        router = Router("user/balance/42.75f", Route("user/balance/:balance"), Activity::class.java, context)
        assertEquals(42.75f, router.intent.getFloatExtra("balance", 0f))
    }

    @Test
    fun testIntent_OneChar() {
        router = Router("last-letter/x", Route("last-letter/:letter"), Activity::class.java, context)
        assertEquals('x', router.intent.getCharExtra("letter", ' '))
    }

    @Test
    fun testIntent_OneDouble() {
        router = Router("amount/123.456d", Route("amount/:amount"), Activity::class.java, context)
        assertEquals(123.456, router.intent.getDoubleExtra("amount", 0.0), 0.0005)
    }

    @Test
    fun testIntent_OneLong() {
        router = Router("amount/123456789L", Route("amount/:amount"), Activity::class.java, context)
        assertEquals(123456789L, router.intent.getLongExtra("amount", 0))
    }

    @Test
    fun testStart() {
        givenDefaultRouter()

        verify(context, Mockito.never()).startActivity(any(Intent::class.java))
        router.start()
        verify(context).startActivity(any(Intent::class.java))
    }

    @Test
    fun testStart_WithIntent() {
        testIntent_OneString_OneFloat()
        router.withIntent {
            it.putExtra("visible", true)
            it.putExtra("foo", "bar")
        }.start()
        assertEquals(true, router.intent.getBooleanExtra("visible", false))
        assertEquals("bar", router.intent.getStringExtra("foo"))
        verify(context).startActivity(any(Intent::class.java))
    }

    @Test
    fun testStart_ForResult() {
        givenDefaultRouter()

        verifyNoMoreInteractions(activity)
        router.startForResult(activity, 123)
        verify(activity).startActivityForResult(any(Intent::class.java), eq(123))
    }

    private fun givenDefaultRouter() = givenRouter("user/42/profile", Route("user/:id/profile"))

    private fun givenRouter(url: String, route: Route) {
        router = Router(url, route, Activity::class.java, context)
    }
}