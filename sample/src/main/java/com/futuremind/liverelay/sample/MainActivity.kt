package com.futuremind.liverelay.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.futuremind.liverelay.LiveRelay

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val relay = LiveRelay<Unit>()

        relay.nextState(Unit)

    }
}
