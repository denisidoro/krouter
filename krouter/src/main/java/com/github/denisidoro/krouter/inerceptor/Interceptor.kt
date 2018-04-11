package com.github.denisidoro.krouter.inerceptor

import com.github.denisidoro.krouter.Route
import com.github.denisidoro.krouter.Router

/**
 * <br/>
 * sunxd<br/>
 * sunxd14@gmail.com<br/>
 * 2018/4/10 上午11:20<br/>
 */
interface Interceptor {

    fun intercept(chain: Chain): Router?

    interface Chain {
        fun proceed(request: Route?): Router?
        fun request(): Route?
    }
}