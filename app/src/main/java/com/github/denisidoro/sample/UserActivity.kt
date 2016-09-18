package com.github.denisidoro.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user: Int = intent.getIntExtra("id", 0)
        (findViewById(R.id.helloTV) as TextView).text = "Your user id is $user"
    }
}
