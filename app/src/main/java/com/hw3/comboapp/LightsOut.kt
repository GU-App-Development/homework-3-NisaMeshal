package com.hw3.comboapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

const val GAME_STATE = "gameState"
const val DEFAULT_GAME = "No games played yet."

class LightsOut : AppCompatActivity() {
    private lateinit var game: LightsOutModel
    private lateinit var lightGridLayout: GridLayout
    private var lightOnColor = 0
    private var lightOffColor = 0
    private var lightOnColorId = 0
    private var gamesWon: Int = 0
    var gameMessage: String = "Lights Out games won today: "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights_out)

        lightOnColorId = R.color.yellow
        lightGridLayout = findViewById(R.id.light_grid)

        // Add the same click handler to all grid buttons
        for (gridButton in lightGridLayout.children) {
            gridButton.setOnClickListener(this::onLightButtonClick)
        }

        lightOnColor = ContextCompat.getColor(this, R.color.yellow)
        lightOffColor = ContextCompat.getColor(this, R.color.black)

        game = LightsOutModel()

        if(savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getString(GAME_STATE)!!
            setButtonColors()
        }

        val newGameButton = findViewById<Button>(R.id.new_game_button)
        newGameButton.setOnClickListener {
            startGame()
        }

        val helpButton = findViewById<Button>(R.id.help_button)
        helpButton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        val colorButton = findViewById<Button>(R.id.change_color_button)
        colorButton.setOnClickListener {
            val intent = Intent(this, ColorActivity::class.java)
            intent.putExtra(EXTRA_COLOR, lightOnColorId)
            colorResultLauncher.launch(intent)
        }

        val shareGame = findViewById<Button>(R.id.share_game)
        shareGame.setOnClickListener {
            val intent = Intent()
            if(gamesWon == 0) {
                intent.putExtra("games_won", DEFAULT_GAME)
                setResult(RESULT_OK, intent)
            } else {
                gameMessage = "$gameMessage$gamesWon!"
                intent.putExtra("games_won", gameMessage)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.switch_button -> {
                val intent = Intent(this, PizzaParty::class.java)
                startActivity(intent)
                true
            }
            R.id.landing_button -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, game.state)
    }

    private fun startGame() {
        game.newGame()
        setButtonColors()
    }

    private fun onLightButtonClick(view: View) {
        // Find the button's row and col
        val buttonIndex = lightGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        game.selectLight(row, col)
        setButtonColors()

        // Congratulate the user if the game is over
        if (game.isGameOver) {
            gamesWon++
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonColors() {
        // Set all buttons' background color
        for (buttonIndex in 0 until lightGridLayout.childCount) {
            val gridButton = lightGridLayout.getChildAt(buttonIndex)

            // Find the button's row and col
            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isLightOn(row, col)) {
                gridButton.setBackgroundColor(lightOnColor)
            } else {
                gridButton.setBackgroundColor(lightOffColor)
            }
        }
    }

    val colorResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            lightOnColorId = result.data!!.getIntExtra(EXTRA_COLOR, R.color.yellow)
            lightOnColor = ContextCompat.getColor(this, lightOnColorId)
            setButtonColors()
        }
    }
}