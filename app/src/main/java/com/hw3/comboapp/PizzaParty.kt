package com.hw3.comboapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PizzaParty : AppCompatActivity() {

    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungryRadioGroup: RadioGroup

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
            val numAttend = numAttendStr.toInt()

            //radio group selection
            val hungerLevel = when (howHungryRadioGroup.checkedRadioButtonId) {
                R.id.light_radio_button -> PizzaCalculator.HungerLevel.LIGHT
                R.id.medium_radio_button -> PizzaCalculator.HungerLevel.MEDIUM
                else -> PizzaCalculator.HungerLevel.RAVENOUS
            }

            //number of pizzas needed
            val calc = PizzaCalculator(numAttend, hungerLevel)
            val totalPizzas = calc.totalPizzas

            //display
            val totalText = getString(R.string.total_pizzas, totalPizzas)
            numPizzasTextView.text = totalText
        }
    }
}