package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.github.denisidoro.krouter.Schema.Type.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
@RunWith(RobolectricGradleTestRunner::class)
class KrouterTest {

    @Mock
    lateinit var context: Context

    lateinit var krouter: Krouter

    val routes = arrayOf(
            Route("user/:id/likes"),
            Route("users"),
            Route("user/:id/photo/:pid"))

    val routes2 = arrayOf(
            Route("user/:id/likes", hashMapOf(Pair("id", Schema(INT)))),
            Route("user/:uid/balance/:money", hashMapOf(Pair("uid", Schema(INT)), Pair("money", Schema(FLOAT)))),
            Route("user/:name/profile", hashMapOf(Pair("name", Schema(STRING))))
    )

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFind() {
        givenSimpleKrouter()

        assertEquals(routes[0], krouter.find("user/3/likes"))
        assertEquals(routes[1], krouter.find("users"))
        assertEquals(routes[2], krouter.find("user/56/photo/432"))

        givenAdvancedKrouter()

        assertEquals(routes2[0], krouter.find("user/3/likes"))
    }

    @Test
    fun testGetRouter() {
        givenSimpleKrouter()

        val url = "user/3/likes"
        assertEquals(Router(url, routes[0], Activity::class.java, context), krouter.getRouter(url)!!)
    }

    @Test
    fun testStart() {
        givenSimpleKrouter()

        krouter.start("user/3/likes")
        verify(context).startActivity(any(Intent::class.java))
    }

    @Test
    fun testMatchesSchema() {
        givenSimpleKrouter()

        routes[0].let {
            assertMatches(true, it, "user/3/likes")
            assertMatches(true, it, "user/a/likes")
            assertMatches(true, it, "user/3abc/likes")
            assertMatches(false, it, "user/3/like")
        }

        routes[1].let {
            assertMatches(true, it, "users")
            assertMatches(false, it, "user")
            assertMatches(false, it, "userb")
            assertMatches(false, it, "users/3")
            assertMatches(false, it, "/users")
            assertMatches(false, it, "users/")
        }

        routes[2].let {
            assertMatches(true, it, "user/3/photo/432")
            assertMatches(true, it, "user/3/photo/abc")
            assertMatches(true, it, "user/def/photo/abc")
            assertMatches(false, it, "user/def/photo/abc/2")
            assertMatches(false, it, "use/3/photo/abc/432")
        }

        givenAdvancedKrouter()

        routes2[0].let {
            assertMatches(true, it, "user/3/likes")
            assertMatches(true, it, "user/3/likes")
            assertMatches(true, it, "user/-3/likes")
            assertMatches(false, it, "user/a/likes")
            assertMatches(false, it, "user/3abc/likes")
            assertMatches(false, it, "user/3.0/likes")
            assertMatches(false, it, "user/-3.0/likes")
            assertMatches(false, it, "user/3/like")
        }

        routes2[1].let {
            assertMatches(true, it, "user/156/balance/42.7")
            assertMatches(false, it, "user/john/balance/42.7")
            assertMatches(true, it, "user/156/balance/42")
            assertMatches(true, it, "user/156/balance/42.0")
        }

        routes2[2].let {
            assertMatches(true, it, "user/john/profile")
            assertMatches(true, it, "user/156/profile")
            assertMatches(true, it, "user/42.7/profile")
            assertMatches(true, it, "user/42.7/profile")
        }
    }

    private fun givenSimpleKrouter() = givenKrouter(routes)

    private fun givenAdvancedKrouter() = givenKrouter(routes2)

    private fun givenKrouter(routes: Array<Route>) {
        val routeMap = HashMap<Route, Class<out Activity>>()
        routes.forEach { routeMap.put(it, Activity::class.java) }
        krouter = Krouter(context, routeMap)
    }

    private fun assertMatches(expected: Boolean, r: Route, s: String) {
        assertEquals(expected, krouter.matchesSchema(s, r))
    }
}
