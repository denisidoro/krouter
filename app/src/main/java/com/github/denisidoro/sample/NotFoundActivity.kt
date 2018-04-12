package com.github.denisidoro.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.widget.TextView

class NotFoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_found)

        val text = intent.getStringExtra("nav")
        (findViewById(R.id.txvTip) as TextView).text = "URL: $text is not specified, redirect to a page "

        val webView = findViewById(R.id.webView) as WebView
        webView.loadUrl("https://github.com/denisidoro/krouter")
    }
}
