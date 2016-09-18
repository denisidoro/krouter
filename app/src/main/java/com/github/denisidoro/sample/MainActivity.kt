package com.github.denisidoro.sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.denisidoro.krouter.Krouter
import com.github.denisidoro.krouter.Route

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val krouter = Krouter(this, hashMapOf(
                Pair(Route("user"), UserActivity::class.java)
        ))

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { krouter.start("user") }
    }

}
