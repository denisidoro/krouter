package com.github.denisidoro.sample

import android.app.Application
import com.github.denisidoro.krouter.inerceptor.Interceptor
import com.github.denisidoro.krouter.Krouter
import com.github.denisidoro.krouter.Router

/**
 * <br/>
 * sunxd<br/>
 * sunxd14@gmail.com<br/>
 * 2018/4/11 下午1:07<br/>
 */
class App : Application(){

    var login = false

    val krouter by lazy {
        Krouter.from(this, hashMapOf(
                "user/:id" to UserActivity::class.java,
                "main" to MainActivity::class.java,
                "fragment" to FragmentMain::class.java,
                "login" to LoginActivity::class.java
        ))
    }

    override fun onCreate() {
        super.onCreate()

        //URL is not specified, Redirect to NotFoundActivity
        krouter.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Router? {

                var route = chain.request()
                var router = chain.proceed(route)
                if(router?.state == Krouter.NOT_FOUNT) {
                    router.activityCls = NotFoundActivity::class.java
                    router.intent.putExtra("nav", route?.url)
                    router.update()
                }
                return router
            }

        })

        //If you are not logged in, go to login
        krouter.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Router? {

                val route = chain.request()
                if(route?.url == "user/:id" && !login) {
                    val target = chain.proceed(route)
                    val router = krouter.getRouter("login")
                    router?.intent?.putExtra("nav", target?.url)
                    router?.update()
                    return router
                }
                return chain.proceed(route)
            }

        })
    }
}