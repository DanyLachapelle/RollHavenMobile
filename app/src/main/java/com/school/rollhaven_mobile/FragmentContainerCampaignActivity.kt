package com.school.rollhaven_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.school.rollhaven_mobile.repositories.IRollHavenRepository
import com.school.rollhaven_mobile.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentContainerCampaignActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container_campaign)

        val viewCampaignsButton = findViewById<Button>(R.id.btn_view_campaigns)
        val joinCampaignButton = findViewById<Button>(R.id.btn_join_campaign)
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)
        val joinCampaignMessageTextView = findViewById<FrameLayout>(R.id.frame_join_campaign_message)
        val launchCampaignButton = findViewById<Button>(R.id.btn_activity_fragment_container_campaign_launch_campaign)
        val scanQrCodeButton = findViewById<Button>(R.id.btn_scan_qr_code)

        val userId = getUserId()

        viewCampaignsButton.setOnClickListener {
            // Cacher le TextView et afficher le fragment des campagnes
            joinCampaignMessageTextView.visibility = View.GONE
            showCampaignsFragment(userId)  // Passer l'ID utilisateur au fragment
        }



        joinCampaignButton.setOnClickListener {
            joinCampaignMessageTextView.visibility = View.GONE
            scanQrCodeButton.visibility = View.VISIBLE // Afficher le bouton QR Code
            showPublicCampaignsFragment()  // Afficher le fragment des campagnes publiques
        }

        scanQrCodeButton.setOnClickListener {
            // Appeler une méthode pour lancer le lecteur QR Code
            launchQrCodeScanner()
        }
        launchCampaignButton.setOnClickListener {
            val intent = Intent(this, ActivityCampaign::class.java)
            startActivity(intent) // Lancer l'activité
        }


        // Si c'est la première fois que l'Activity est lancée, ajouter le fragment
        if (savedInstanceState == null) {
            val fragment = CampaignsFragment()

            // Créer un Bundle et y ajouter l'ID utilisateur
            val bundle = Bundle()
            bundle.putInt("USER_ID", userId)  // Passez l'ID utilisateur ici
            fragment.arguments = bundle

            // Remplacer le fragment dans le conteneur
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment) // 'fragment_container' est l'ID du conteneur dans le layout
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = com.google.zxing.integration.android.IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Afficher le contenu scanné dans un Toast (pour débogage)
                val qrCodeContent = result.contents
                Log.i("QrCodeLog", qrCodeContent)
                Toast.makeText(this, "QR Code: $qrCodeContent", Toast.LENGTH_LONG).show()

                // Récupérer l'ID utilisateur à partir des SharedPreferences
                val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val userId = sharedPreferences.getInt("user_id", -1) // Remplacez -1 par la valeur par défaut si l'ID est introuvable

                if (userId != -1) {
                    // Appeler l'API pour rejoindre la campagne
                    joinCampaign(qrCodeContent, userId)
                } else {
                    Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun joinCampaign(invitationCode: String, userId: Int) {
        // Utilisation de l'instance Retrofit via ApiClient
        val service = ApiClient.apiService

        // Appel à l'API pour rejoindre la campagne privée
        service.joinPrivateCampaign(invitationCode, userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Si la requête est réussie, afficher un message de confirmation
                    Log.i("QrCodeLog", "Campagne rejointe avec succès")
                    Toast.makeText(this@FragmentContainerCampaignActivity, "Campagne rejointe avec succès", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i("QrCodeLog", "Erreur : ${response.code()} ${response.message()}")
                    // Afficher un message d'erreur en cas d'échec
                    Toast.makeText(this@FragmentContainerCampaignActivity, "Échec de rejoindre la campagne", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Afficher un message d'erreur en cas de problème réseau
                Log.i("QrCodeLog", "Erreur réseau : ${t.message}")
                Toast.makeText(this@FragmentContainerCampaignActivity, "Erreur réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun launchQrCodeScanner() {
        val intentIntegrator = com.google.zxing.integration.android.IntentIntegrator(this)
        intentIntegrator.setPrompt("Scan a QR Code") // Message affiché pendant le scan
        intentIntegrator.setBeepEnabled(true) // Activer le son après scan
        intentIntegrator.setOrientationLocked(false) // Permettre la rotation
        intentIntegrator.initiateScan() // Démarrer le scan
    }


    private fun getUserId(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 0)
        return userId
        Log.d("Activity", "Stored User ID: $userId")
    }

    private fun showCampaignsFragment(userId: Int) {
        val fragment = CampaignsFragment()

        // Créer un Bundle et y ajouter l'ID utilisateur
        val bundle = Bundle()
        bundle.putInt("USER_ID", userId)  // Passez l'ID utilisateur ici
        fragment.arguments = bundle

        // Remplacer le fragment dans le conteneur
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)  // 'fragment_container' est l'ID du conteneur dans le layout
            .commit()
    }

    private fun showPublicCampaignsFragment() {
        val fragment = CampaignPublicFragment()

        // Remplacer le fragment dans le conteneur
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)  // 'fragment_container' est l'ID du conteneur dans le layout
            .commit()
    }
}