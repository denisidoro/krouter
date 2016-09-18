package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.github.denisidoro.krouter.Schema.Type.STRING

class Router(val url: String, val route: Route, activityCls: Class<out Activity>, val context: Context) {

    val intent = Intent(context, activityCls)

    init {
        putExtras(url, route)
    }

    fun start() {
        context.startActivity(intent)
    }

    internal fun putExtras(url: String, route: Route) {
        url.split('/').toTypedArray()
                .zip(route.url.split('/').toTypedArray())
                .filter { it.second.startsWith(':') }
                .map { Pair(it.first, it.second.substring(1)) }
                .forEach {
                    val regex = route.schemas[it.second]?.regex?.let { Schema.Type.from(it) } ?: inferRegex(it.first)
                    when (regex) {
                        Schema.Type.INT -> intent.putExtra(it.second, it.first.toInt())
                        Schema.Type.FLOAT -> intent.putExtra(it.second, it.first.toFloat())
                        else -> intent.putExtra(it.second, it.first.toString())
                    }
                }
    }

    internal fun inferRegex(seg: String): Schema.Type {
        return Schema.Type.values()
                .find { seg.matches(it.regex.toRegex()) } ?: STRING
    }

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is Router) return false

        if (url != other.url) return false
        if (route != other.route) return false

        return true
    }

}
