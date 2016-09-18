package com.github.denisidoro.krouter

/*
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
        initMocks(this)
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

        krouter.getRouter("user/3/likes")!!.intent.let {
            assertEquals(3, it.getIntExtra("id", 0))
        }
    }

    @Test
    fun testStart() {

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

    private fun givenSimpleKrouter() {
        val routeMap = HashMap<Route, Class<out Activity>>()
        routes.forEach { routeMap.put(it, Activity::class.java) }
        krouter = Krouter(context, routeMap)
    }

    private fun givenAdvancedKrouter() {
        val routeMap = HashMap<Route, Class<out Activity>>()
        routes2.forEach { routeMap.put(it, Activity::class.java) }
        krouter = Krouter(context, routeMap)
    }


    private fun assertMatches(expected: Boolean, r: Route, s: String) {
        assertEquals(expected, krouter.matchesSchema(s, r))
    }
}
*/