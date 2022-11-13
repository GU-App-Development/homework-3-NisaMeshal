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

        val pizzaButton = findViewById<Button>(R.id.pizza_app)
        pizzaButton.setOnClickListener {
            val intent = Intent(this, PizzaParty::class.java)
            startActivity(intent)
        }

        val lightsOutButton = findViewById<Button>(R.id.lightsout_app)
        lightsOutButton.setOnClickListener {
            val intent = Intent(this, LightsOut::class.java)
            startActivity(intent)
        }
    }
}