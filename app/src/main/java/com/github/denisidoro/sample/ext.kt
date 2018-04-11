package com.github.denisidoro.sample

import android.app.Activity
import android.support.v4.app.Fragment

/**
 * <br/>
 * sunxd<br/>
 * sunxd14@gmail.com<br/>
 * 2018/4/11 下午1:12<br/>
 */
val Activity.app : App
    get() = application as App

val Fragment.app : App
    get() = activity.app

