package com.example.cocogoatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseRef = firebaseDatabase.getReference("messages")

        mAuth = FirebaseAuth.getInstance()

        mAuth!!.signInWithEmailAndPassword("qiqi@cocogoat.com", "adeptibeast")
            .addOnCompleteListener{
                task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Signed in ayeeee", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Signed in not ayeeeee", Toast.LENGTH_LONG).show()
                }
            }

        var employee = Employee("uamee", "Hardbass Producer",
        "Mother Russia", 25)

        //databaseRef.setValue(employee)

        databaseRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var value = dataSnapshot.value as HashMap<String, Any>
                //Log.d("VALUE: ", value.get("name").toString())
            }

            override fun onCancelled(error:DatabaseError) {
                Log.d("ERROR: ", error.message)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "User is INSIDE DA BUILDING!!!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "User is OUTSIDE DA BUILDING!!!", Toast.LENGTH_LONG).show()
        }
    }

    class Employee() {
        var name: String? = null
        var position: String? = null
        var homeAddress: String? = null
        var age: Int? = null

        constructor(name: String, position: String, homeAddress: String, age: Int) : this() {
            this.name = name
            this.homeAddress = homeAddress
            this.age = age
            this.position = position
        }
    }
}
