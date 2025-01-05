package com.school.rollhaven_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.school.rollhaven_mobile.repositories.IRollHavenRepository
import com.school.rollhaven_mobile.repositories.LoginResponse
import com.school.rollhaven_mobile.repositories.UserLogin
import com.school.rollhaven_mobile.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private fun loginUser(pseudo: String, password: String) {
        val userLogin = UserLogin(pseudo, password)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5277/") // Assurez-vous d'utiliser l'adresse correcte
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IRollHavenRepository::class.java)
        service.login(userLogin).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Si la connexion est réussie, récupérer l'ID de l'utilisateur depuis la réponse
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val userId = loginResponse.id
                        val token = loginResponse.token // Vous pouvez aussi stocker le token si nécessaire

                        Log.d("LoginActivity", "LoginResponse: userId = $userId, token = $token")
                        // Sauvegarder l'ID de l'utilisateur dans SharedPreferences
                        if (userId != null) {
                            saveUserId(userId)
                        }


                        val intent = Intent(this@ActivityLogin, FragmentContainerCampaignActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("LoginActivity", "Response body is null")
                    }
                } else {
                    Log.e("LoginActivity", "Login failed: ${response.message()}")
                    Toast.makeText(this@ActivityLogin, "Échec de la connexion", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Failed to login", t)
                Toast.makeText(this@ActivityLogin, "Erreur réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserId(userId: Int) {
        if (userId != 0) {
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("user_id", userId)
            editor.apply()
            Log.d("LoginActivity", "User ID saved: $userId") // Afficher l'ID dans Logcat pour vérifier
        } else {
            Log.e("LoginActivity", "Invalid user ID: $userId")
        }
    }
    }
