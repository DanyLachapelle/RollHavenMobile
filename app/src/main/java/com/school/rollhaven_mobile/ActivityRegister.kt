package com.school.rollhaven_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.school.rollhaven_mobile.repositories.RegisterResponse
import com.school.rollhaven_mobile.repositories.UserRegister
import com.school.rollhaven_mobile.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityRegister : AppCompatActivity() {


    private lateinit var etPseudo: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialisation des vues
        etPseudo = findViewById(R.id.et_activiy_register_pseudo)
        etEmail = findViewById(R.id.et_activiy_register_email)
        etPassword = findViewById(R.id.et_activiy_register_password)
        btnRegister = findViewById(R.id.btn_activiy_register_register)

        btnRegister.setOnClickListener {
            // Récupérer les données des champs
            val pseudo = etPseudo.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Valider les champs avant de faire la requête
            if (pseudo.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(pseudo, email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(pseudo: String, email: String, password: String) {
        // Créer un objet User avec les données d'inscription
        val user = UserRegister(pseudo, email, password)

        // Envoyer la requête API pour l'enregistrement
        ApiClient.apiService.register(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ActivityRegister, "Registration Successful", Toast.LENGTH_SHORT).show()

                    // Rediriger vers l'écran de connexion ou MainView
                    val intent = Intent(this@ActivityRegister, FragmentContainerCampaignActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@ActivityRegister, "Registration failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ActivityRegister, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}