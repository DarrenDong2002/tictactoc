package com.example.tictactoc

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class updateStatus {
    private val db = Firebase.firestore
    fun updateScore(usid: String){
        val docRef = db.collection("users").document(usid)
        var score: Long = 0
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    score = documentSnapshot.get("numberOfWins") as Long
                    score +=1
                    docRef.update("numberOfWins", score)
                }
            }
        val collectionRef = db.collection("users")

        // order by "username" field in ascending order
        collectionRef.orderBy("numberOfWins", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val username = document.getLong("numberOfWins")
                    // use the username value here
                }
            }
    }
}