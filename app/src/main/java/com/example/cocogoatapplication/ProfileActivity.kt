package com.example.cocogoatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var firebaseDatabase = FirebaseDatabase.getInstance()
        //var databaseRef = firebaseDatabase.getReference("messages")

        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signInWithEmailAndPassword(intent.getStringExtra("email").toString(),
            intent.getStringExtra("password").toString())
            .addOnCompleteListener{
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    currentUser = mAuth!!.currentUser
                    Toast.makeText(this, "Signed in.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Not signed in.", Toast.LENGTH_LONG).show()
                }
            }
        val btn = findViewById<Button>(R.id.saveBtn)
        btn.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString().trim()
            val surname = findViewById<EditText>(R.id.surname).text.toString().trim()
            if (name != "" || surname != "") {
                val user = User(name, surname, intent.getStringExtra("email").toString())
                var databaseRef = firebaseDatabase.getReference("users")
                    .child(intent.getStringExtra("email").toString().substringBefore("@"))
                databaseRef.setValue(user)
            } else {
                Toast.makeText(this, "Information is not full.", Toast.LENGTH_LONG).show()
            }
        }
    }

    class User() {
        var firstName: String? = null
        var surname: String? = null
        var email: String? = null

        constructor(firstName: String, surname: String, email: String) : this() {
            this.firstName = firstName
            this.surname = surname
            this.email = email
        }
    }
}
