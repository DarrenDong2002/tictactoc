package com.example.tictactoc

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class updateStatus {
    private val db = Firebase.firestore
    fun updateScore(usid: String){
        print("in")
        val docRef = db.collection("users").document(usid)
        var score: Int = 0
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    score = document.getLong("numberOfWins")?.toInt()!!
                }
            }
        docRef.update("numberOfWins", score+1)
    }
}