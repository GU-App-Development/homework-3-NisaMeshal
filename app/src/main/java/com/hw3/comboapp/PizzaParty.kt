package com.hw3.comboapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val DEFAULT_MESSAGE: String = "No pizza calculated yet!"

class PizzaParty : AppCompatActivity() {
    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungryRadioGroup: RadioGroup
    lateinit var calculatedPizza: String
    var totalPizzas: Int = 0
    var numAttend: Int = 0
    lateinit var hungerLevelStr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_party)
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)
        howHungryRadioGroup = findViewById(R.id.hungry_radio_group)

        val calculateButton = findViewById<Button>(R.id.calc_button)
        calculateButton.setOnClickListener {
            //grab from edit text and convert to int
            val numAttendStr = numAttendEditText.text.toString()
            numAttend = numAttendStr.toInt()

            //radio group selection
            val hungerLevel = when (howHungryRadioGroup.checkedRadioButtonId) {
                R.id.light_radio_button -> PizzaCalculator.HungerLevel.LIGHT
                R.id.medium_radio_button -> PizzaCalculator.HungerLevel.MEDIUM
                else -> PizzaCalculator.HungerLevel.RAVENOUS
            }

            hungerLevelStr = hungerLevel.toString()

            //number of pizzas needed
            val calc = PizzaCalculator(numAttend, hungerLevel)
            totalPizzas = calc.totalPizzas

            //display
            val totalText = getString(R.string.total_pizzas, totalPizzas)
            numPizzasTextView.text = totalText
            calculatedPizza = totalText
        }

        val intent = Intent(this, MainActivity::class.java)
        if(totalPizzas == 0) {
            intent.putExtra("pizza_calc", DEFAULT_MESSAGE)
        } else {
            val messageString = "$calculatedPizza. People attending: $numAttend. Hunger level: $hungerLevelStr"
            intent.putExtra("pizza_calc", messageString)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.switch_button -> {
                val intent = Intent(this, LightsOut::class.java)
                startActivity(intent)
                true
            }
            R.id.landing_button -> {
                val intent = Intent(this, MainActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}