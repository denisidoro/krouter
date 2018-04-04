package com.github.denisidoro.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * <br/>
 * sunxd<br/>
 * sunxd14@gmail.com<br/>
 * 2018/4/3 下午4:46<br/>
 */
class FragmentMain: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = view?.findViewById(R.id.txvTitle) as TextView
        tv.text = "${tv.text}{id: ${arguments["id"]}, name: ${arguments["name"]}}"

    }
}