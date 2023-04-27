package com.example.tictactoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.example.tictactoc.databinding.ActivityProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        // Hiding the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(binding.root)

        val signoutbutton = findViewById<Button>(R.id.btn_signout)

        signoutbutton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)

        }

        findViewById<BottomNavigationView>(R.id.bottom_navigationn).setOnItemSelectedListener {
                item->

            when (item.itemId) {

                R.id.action_home -> {
                    val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }


    }
}