package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import java.util.*

class Krouter(val context: Context, val routes: Map<Route, Class<out Activity>> = HashMap()) {

    fun start(url: String) = getRouter(url)!!.let { it.start() }

    fun getRouter(url: String) = find(url)?.let { Router(url, it, routes[it]!!, context) }

    internal fun find(url: String): Route? = routes.keys.find { matchesSchema(url, it) }

    internal fun matchesSchema(url: String, route: Route): Boolean {
        val urlSegs = split(url)
        val routeUrlSegs = split(route.url)

        if (routeUrlSegs.size != urlSegs.size)
            return false

        return urlSegs
                .zip(routeUrlSegs)
                .all {
                    if (it.second.startsWith(':')) {
                        it.second.substring(1).let { seg ->
                            route.schemas[seg]?.regex?.let { regex ->
                                regex.length == 0 || it.first.matches(regex.toRegex())
                            } ?: true
                        }
                    } else it.first.equals(it.second)
                }
    }

    private fun split(s: String): Array<String> = s.split('/').toTypedArray()

    companion object {
        fun from(context: Context, routes: Map<String, Class<out Activity>>): Krouter {
            val r = HashMap<Route, Class<out Activity>>()
            routes.forEach { r.put(Route(it.key), it.value) }
            return Krouter(context, r)
        }
    }

}