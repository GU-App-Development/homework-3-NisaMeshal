package com.hw3.comboapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

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

        val sendButton = findViewById<Button>(R.id.send_message)
        sendButton.setOnClickListener {
            val intent = intent
            intent.putExtra(Intent.EXTRA_TITLE, "Share")
            val pizzaMessage : String = intent.getStringExtra("pizza_calc").toString()
            val loMessage : String = intent.getStringExtra("games_won").toString()
            val userInput = findViewById<EditText>(R.id.user_text)
            val userMessage : String = userInput.text.toString()

            val dataStrings: ArrayList<String> = arrayListOf(
                userMessage,
                loMessage,
                pizzaMessage
            )

            val shareIntent = Intent.createChooser(Intent().apply{
                action = Intent.ACTION_SEND_MULTIPLE
                putStringArrayListExtra(Intent.EXTRA_STREAM, dataStrings)
            }, null)
            startActivity(shareIntent)
        }
    }
}