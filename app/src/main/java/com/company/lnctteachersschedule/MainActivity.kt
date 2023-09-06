package com.company.lnctteachersschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore


        //to enter the detail in firebase ,
val teacher = db.collection("teachers")




        val daySpinner: Spinner = findViewById(R.id.days)
        val periodSpinner: Spinner = findViewById(R.id.period)
        val submitButton: Button = findViewById(R.id.button)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val teachers = mutableListOf<Teacher>()
        val adapter = TeacherAdapter(teachers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        submitButton.setOnClickListener {
            val inputDay = daySpinner.selectedItem.toString()
            val inputPeriod = periodSpinner.selectedItem.toString()

            if (inputDay.isNotEmpty() && inputPeriod.isNotEmpty()) {

               val value = "${inputDay}_$inputPeriod"
                Log.d("checkmate", "onCreate: $value")

                db.collection("teachers")
                    .whereEqualTo(value, "")
                    .get()
                    .addOnSuccessListener { documents ->

                        val teachersList = mutableListOf<Teacher>()
                        for (document in documents) {
                            val name = document.getString("Name")
                            val teacherCode = document.getString("Teacher_code")
                            val contactno = document.getString("contact_no") // Assuming "Contact No." is a String

                            if (name != null && teacherCode != null && contactno != null) {
                                val teacher = Teacher(name, teacherCode, contactno)
                                teachersList.add(teacher)
                            } else {
                                Log.d("checkmac", "Some fields are missing in the document ${document.id}")
                            }
                        }


                        adapter.updateData(teachersList)


                    }
                    .addOnFailureListener { exception ->
                        Log.w("checkmac", "Error getting documents: ", exception)
                    }



                val toast = Toast.makeText(this@MainActivity, "Loading...", Toast.LENGTH_SHORT)
                val toastDurationInMilliseconds = 100 // 0.5 seconds

                Handler().postDelayed({
                    toast.cancel() // Cancel the toast after the specified duration
                }, toastDurationInMilliseconds.toLong())

                toast.show()



            } else {
                // Handle empty input
                Toast.makeText(this, "Please enter a valid day and period.", Toast.LENGTH_SHORT)
                    .show()
            }


        }

    }
}



