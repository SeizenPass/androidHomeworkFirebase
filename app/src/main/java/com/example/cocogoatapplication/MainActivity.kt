package com.example.cocogoatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseRef = firebaseDatabase.getReference("messages")

        var employee = Employee("uamee", "Hardbass Producer",
        "Mother Russia", 25)

        databaseRef.setValue(employee)

        databaseRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var value = dataSnapshot.value as HashMap<String, Any>
                Log.d("VALUE: ", value.get("name").toString())
            }

            override fun onCancelled(error:DatabaseError) {
                Log.d("ERROR: ", error.message)
            }
        })
    }
    data class Employee(var name: String, var position: String, var homeAddress: String, var age: Int)
}
