package com.example.tictactoc

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoc.databinding.ActivityMainBinding
import android.content.Intent
import android.app.Activity
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val timerViewModel: TimerViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Hiding the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(binding.root)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
                item->

            when (item.itemId) {

                R.id.action_profile -> {
                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }

        val gameOverLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                timerViewModel.resetConsecutiveWins()
                timerViewModel.resumeTimer()
            }
        }

        timerViewModel.gameOver.observe(this, { isGameOver ->
            if (isGameOver) {
                timerViewModel.pauseTimer()
                val intent = Intent(this, GameOverActivity::class.java)
                intent.putExtra("winScore", timerViewModel.consecutiveWins.value ?: 0)
                gameOverLauncher.launch(intent)
            }
        })

        timerViewModel.timerValue.observe(this, { timerValue ->
            binding.timerTextView.text = getString(R.string.timer_format, timerValue / 1000)
        })

        val buttons = listOf(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                timerViewModel.onCellClicked(index)
            }
        }

        timerViewModel.ticTacToeBoard.observe(this, { board ->
            buttons.forEachIndexed { index, button ->
                button.text = board[index]
            }
        })
    }
}

