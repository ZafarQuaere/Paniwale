package com.app.aquahey.purepani.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.app.aquahey.purepani.R


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val b = Intent(applicationContext, LoginActivity::class.java)
            startActivity(b)
            finish()
        }, SPLASH_TIME_OUT.toLong())

    }


}
