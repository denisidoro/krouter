package com.github.denisidoro.sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { app.krouter.start("user/42") }

        val f = app.krouter.getRouter("fragment")!!
                .withBundle {
                    it.putInt("id", 18)
                    it.putString("name", "Shadow")
                }
                .fragment()
        if(supportFragmentManager.findFragmentByTag("fragment") == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, f, "fragment")
                    .commit()
        }


    }


}
