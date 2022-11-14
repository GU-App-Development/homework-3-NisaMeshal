package com.hw3.comboapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var pizzaMessage: String = ""
    var lightsOutMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzaButton = findViewById<Button>(R.id.pizza_app)
        pizzaButton.setOnClickListener {
            val intent = Intent(this, PizzaParty::class.java)
            pizzaResultLauncher.launch(intent)
        }

        val lightsOutButton = findViewById<Button>(R.id.lightsout_app)
        lightsOutButton.setOnClickListener {
            val intent = Intent(this, LightsOut::class.java)
            lightsOutResultLauncher.launch(intent)
        }

        val userInput = findViewById<EditText>(R.id.user_text)

        val sendButton = findViewById<Button>(R.id.send_message)
        sendButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${userInput.text.toString()} \n $pizzaMessage \n $lightsOutMessage")
                type = "text/plain"
            }
            startActivity(shareIntent)
        }

        Log.d(TAG, pizzaMessage)
        Log.d(TAG, lightsOutMessage)
    }

    val pizzaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            pizzaMessage = (result.data!!.getStringExtra("pizza_calc")).toString()
            lightsOutMessage = (result.data!!.getStringExtra("games_won")).toString()
        }
    }

    val lightsOutResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            lightsOutMessage = (result.data!!.getStringExtra("games_won")).toString()
        }
    }
}