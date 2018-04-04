package com.github.denisidoro.sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.denisidoro.krouter.Krouter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val krouter = Krouter.from(this, hashMapOf(
                "user/:id" to UserActivity::class.java,
                "main" to MainActivity::class.java,
                "fragment" to FragmentMain::class.java
        ))

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { krouter.start("user/42") }


        val f = krouter.getRouter("fragment")!!
                .withBundle {
                    it.putInt("id", 18)
                    it.putString("name", "Shadow")
                }
                .fragment()


        supportFragmentManager.beginTransaction()
                .add(R.id.container, f)
                .commit()
    }

}
