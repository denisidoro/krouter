package com.github.denisidoro.krouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.denisidoro.krouter.Schema.Type.*

class Router(url: String, route: Route, val activityCls: Class<out Any>, val context: Context) {

    val intent by lazy { Intent(context, activityCls) }
    //lateinit var activityRef: WeakReference<Activity>
    //var requestCode: Int? = null

    val bundle by lazy {
        Bundle()
    }

    init {
        putExtras(url, route)
    }

    fun start() {
        context.startActivity(intent)
    }

    fun fragment(): Fragment {
        try{
            val fragment = activityCls.newInstance() as Fragment
            fragment.arguments = bundle
            return fragment
        }catch (e: Exception) {
            throw Exception(("%d is not a android.support.v4.app.Fragment, ${e.message}").format(activityCls))
        }
    }

    fun startForResult(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }

    fun withIntent(f: (Intent) -> Unit): Router {
        f(intent)
        return this
    }


    fun withBundle(f: (Bundle) -> Unit): Router {
        f(bundle)
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
                    if(isFragment()) {
                        when (regex) {
                            INT -> bundle.putInt(param, it.first.toInt())
                            FLOAT -> bundle.putFloat(param, (if (it.first.last() == 'f') removeLastChar(it.first) else it.first).toFloat())
                            DOUBLE -> bundle.putDouble(param, removeLastChar(it.first).toDouble())
                            LONG -> bundle.putLong(param, removeLastChar(it.first).toLong())
                            else -> if (it.first.length == 1) bundle.putChar(param, it.first[0]) else bundle.putString(param, it.first)
                        }
                    }else {
                        when (regex) {
                            INT -> intent.putExtra(param, it.first.toInt())
                            FLOAT -> intent.putExtra(param, (if (it.first.last() == 'f') removeLastChar(it.first) else it.first).toFloat())
                            DOUBLE -> intent.putExtra(param, removeLastChar(it.first).toDouble())
                            LONG -> intent.putExtra(param, removeLastChar(it.first).toLong())
                            else -> if (it.first.length == 1) intent.putExtra(param, it.first[0]) else intent.putExtra(param, it.first)
                        }
                    }
                }
    }

    internal fun inferRegex(seg: String): Schema.Type {
        return Schema.Type.values()
                .find { seg.matches(it.regex.toRegex()) } ?: STRING
    }

    private fun removeLastChar(str: String): String = str.substring(0, str.length - 1)

    private fun isFragment() = Fragment::class.java.isAssignableFrom(activityCls)

}
