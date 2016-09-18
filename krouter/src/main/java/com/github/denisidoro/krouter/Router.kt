package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.github.denisidoro.krouter.Schema.Type.*

class Router(url: String, route: Route, activityCls: Class<out Activity>, val context: Context) {

    val intent = Intent(context, activityCls)
    //lateinit var activityRef: WeakReference<Activity>
    //var requestCode: Int? = null

    init {
        putExtras(url, route)
    }

    fun start() {
        context.startActivity(intent)
    }

    fun startForResult(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }

    fun withIntent(f: (Intent) -> Unit): Router {
        f(intent)
        return this
    }

    //fun withActivity(activity: Activity) {
    //    this.activityRef = WeakReference(activity)
    //}

    internal fun putExtras(url: String, route: Route) {
        url.split('/').toTypedArray()
                .zip(route.url.split('/').toTypedArray())
                .filter { it.second.startsWith(':') }
                .forEach {
                    val param = it.second.substring(1)
                    val regex = route.schemas[param]?.regex?.let { Schema.Type.from(it) } ?: inferRegex(it.first)
                    when (regex) {
                        INT -> intent.putExtra(param, it.first.toInt())
                        FLOAT -> intent.putExtra(param, (if (it.first.last() == 'f') removeLastChar(it.first) else it.first).toFloat())
                        DOUBLE -> intent.putExtra(param, removeLastChar(it.first).toDouble())
                        LONG -> intent.putExtra(param, removeLastChar(it.first).toLong())
                        else -> if (it.first.length == 1) intent.putExtra(param, it.first[0]) else intent.putExtra(param, it.first)
                    }
                }
    }

    internal fun inferRegex(seg: String): Schema.Type {
        return Schema.Type.values()
                .find { seg.matches(it.regex.toRegex()) } ?: STRING
    }

    private fun removeLastChar(str: String): String = str.substring(0, str.length - 1)

}
