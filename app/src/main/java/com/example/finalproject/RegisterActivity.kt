
package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var quizTitle: MaterialTextView
    private lateinit var userName: MaterialTextView
    private lateinit var nameField: TextInputEditText
    private lateinit var userEmail: MaterialTextView
    private lateinit var emailField: TextInputEditText
    private lateinit var userPassword: MaterialTextView
    private lateinit var passwordField: TextInputEditText
    private lateinit var confirmPassword: MaterialTextView
    private lateinit var confirmPasswordField: TextInputEditText
    private lateinit var registerButton: MaterialButton

    private lateinit var height: MaterialTextView
    private lateinit var heightField: TextInputEditText
    private lateinit var weight: MaterialTextView
    private lateinit var weightField: TextInputEditText

    //private lateinit var role: MaterialTextView
   // private lateinit var roleField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViews()
        registerButton.setOnClickListener { singUp() }
    }

    private fun findViews() {
        quizTitle = findViewById(R.id.quiz_title)
        userName = findViewById(R.id.user_name)
        nameField = findViewById(R.id.name_field)
        userEmail = findViewById(R.id.user_email)
        emailField = findViewById(R.id.email_field)
        userPassword = findViewById(R.id.user_password)
        passwordField = findViewById(R.id.password_field)
        confirmPassword = findViewById(R.id.user_confirm_password)
        confirmPasswordField = findViewById(R.id.confirm_password_field)
        registerButton = findViewById(R.id.register_button)
        height = findViewById(R.id.user_height)
        heightField = findViewById(R.id.height_field)
        weight = findViewById(R.id.user_weight)
        weightField = findViewById(R.id.weight_field)
      //  role = findViewById(R.id.user_role)
       // roleField = findViewById(R.id.role_field)

    }

    private fun singUp() {
        val name  = nameField.text.toString()
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        val confirmPassword = confirmPasswordField.text.toString()

        if (!checkInput(name,email,password,confirmPassword))
            return

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { createUserTask ->
                if (createUserTask.isSuccessful) {
                    // new user
                    moveToQuizActivity()
                }
                else{
                    singIn(email,password,name)
                }
            }
    }

    private fun checkInput(name: String, email: String, password: String, confirmPassword: String) : Boolean{
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()|| confirmPassword.isEmpty()) {
            Toast.makeText(this, "some data is missing", Toast.LENGTH_LONG).show()
            return false
        }
        if(password.length < 6){
            Toast.makeText(this, "password must be least 6 characters", Toast.LENGTH_LONG).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun singIn(email: String, password: String, name: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                     //exist user
                    moveToMainActivity(name)
                } else {
                    // cant singIn
                  Toast.makeText(this, "Password incorrect", Toast.LENGTH_LONG).show()

                }
            }
    }
    private fun moveToQuizActivity() {
        val i = Intent(this,HealthQuizActivity::class.java)
        startActivity(i)
    }

    private fun moveToMainActivity(name: String) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("userName",name)
        startActivity(intent)
    }
}








