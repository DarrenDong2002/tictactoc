package com.example.tictactoc

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tictactoc.databinding.ActivityGameOverBinding
import android.net.Uri
import android.content.Intent
import android.view.Window

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)

        // Hiding the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(binding.root)

        val winScore = intent.getIntExtra("winScore", 0)
        val scoreTextView: TextView = binding.scoreTextView
        scoreTextView.text = getString(R.string.consecutive_wins, winScore)

        val restartButton: Button = binding.restartButton
        restartButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        val leaderboardButton: Button = binding.leaderboardButton
        leaderboardButton.setOnClickListener {
            val websiteUrl = "https://m4d97xhms8hd7gwaedxzzg.on.drv.tw/www.leaderboard/lb.html" // PLEASE LINK OUR WEBSITE!!
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }
    }
}