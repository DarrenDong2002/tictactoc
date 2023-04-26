package com.example.tictactoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Window
import android.widget.Toast
import com.example.tictactoc.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.TextView


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Hiding the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        /**  binding.btnLogin.setOnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        } **/

        val signupbutton = findViewById<Button>(R.id.btn_signup)

        signupbutton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }


     override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        }
    }
}
