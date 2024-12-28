package com.school.rollhaven_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.school.rollhaven_mobile.repositories.LoginResponse
import com.school.rollhaven_mobile.repositories.UserLogin
import com.school.rollhaven_mobile.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogin: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pseudoEditText = findViewById<EditText>(R.id.et_activiy_register_pseudo)
        val passwordEditText = findViewById<EditText>(R.id.et_activiy_login_password)
        val loginButton = findViewById<Button>(R.id.btn_activiy_login_login)

        loginButton.setOnClickListener {
            val pseudo = pseudoEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (pseudo.isNotEmpty() && password.isNotEmpty()) {
                loginUser(pseudo,password)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
        private fun loginUser(pseudo: String,password: String) {
            ApiClient.apiService.login(UserLogin(pseudo,password)).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        Toast.makeText(this@ActivityLogin, "Login successful: $token", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ActivityLogin, MainView::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@ActivityLogin, "Login failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@ActivityLogin, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
