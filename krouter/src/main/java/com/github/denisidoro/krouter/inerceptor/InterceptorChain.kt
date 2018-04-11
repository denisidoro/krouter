package com.github.denisidoro.krouter.inerceptor

import com.github.denisidoro.krouter.Route
import com.github.denisidoro.krouter.Router

/**
 * <br/>
 * sunxd<br/>
 * sunxd14@gmail.com<br/>
 * 2018/4/10 下午1:46<br/>
 */
class InterceptorChain(val interceptors: ArrayList<Interceptor>,
                       val index: Int,
                       val route: Route?,
                       var router: Router?): Interceptor.Chain {


    override fun proceed(request: Route?): Router? {
        val next = InterceptorChain(interceptors, index + 1, route, router)
        if(index >= interceptors.size) return router
        val interceptor = interceptors[index]
        router = interceptor.intercept(next)
        return router
    }

    override fun request(): Route? = route
}