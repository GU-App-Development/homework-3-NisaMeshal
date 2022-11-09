package com.hw3.comboapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onPizzaClick(view: View) {
        val intent = Intent(this, PizzaParty::class.java)
        startActivity(intent)
    }

    fun onLightsoutClick(view: View) {
        val intent = Intent(this, LightsOut::class.java)
        startActivity(intent)
    }
}