package com.github.denisidoro.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById(R.id.btnLogin).setOnClickListener {
            app.login = true
            val url = intent.getStringExtra("nav")
            if(!TextUtils.isEmpty(url)) {
                Toast.makeText(this, "Login success, should nav $url", Toast.LENGTH_LONG).show()
                app.krouter.start(intent.getStringExtra("nav"))
            }else {
                Toast.makeText(this, "Login success", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }
}
