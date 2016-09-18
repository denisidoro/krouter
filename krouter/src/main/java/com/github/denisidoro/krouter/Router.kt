package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.github.denisidoro.krouter.Schema.Type.*

class Router(url: String, route: Route, activityCls: Class<out Activity>, val context: Context) {

    val intent = Intent(context, activityCls)

    init {
        url.split('/').toTypedArray()
                .zip(route.url.split('/').toTypedArray())
                .filter { it.second.startsWith(':') }
                .map { Pair(it.first, it.second.substring(1)) }
                .forEach {
                    val regex = route.schemas[it.second]?.regex?.let { Schema.Type.from(it) } ?: inferRegex(it.first)
                    when (regex) {
                        INT -> intent.putExtra(it.second, it.first.toInt())
                        FLOAT -> intent.putExtra(it.second, it.first.toFloat())
                        else -> intent.putExtra(it.second, it.first.toString())
                    }
                }
    }

    fun start() {
        context.startActivity(intent)
    }

    internal fun inferRegex(seg: String): Schema.Type {
        return Schema.Type.values()
                .find { seg.matches(it.regex.toRegex()) } ?: STRING
    }
}
