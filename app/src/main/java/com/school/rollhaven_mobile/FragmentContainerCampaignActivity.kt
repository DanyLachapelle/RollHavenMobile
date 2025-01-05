package com.school.rollhaven_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FragmentContainerCampaignActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container_campaign)

        val viewCampaignsButton = findViewById<Button>(R.id.btn_view_campaigns)
        val joinCampaignButton = findViewById<Button>(R.id.btn_join_campaign)
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)
        val joinCampaignMessageTextView = findViewById<FrameLayout>(R.id.frame_join_campaign_message)
        val launchCampaignButton = findViewById<Button>(R.id.btn_activity_fragment_container_campaign_launch_campaign)

        val userId = getUserId()

        viewCampaignsButton.setOnClickListener {
            // Cacher le TextView et afficher le fragment des campagnes
            joinCampaignMessageTextView.visibility = View.GONE
            showCampaignsFragment(userId)  // Passer l'ID utilisateur au fragment
        }

        // Lorsque l'utilisateur clique sur "Join Campaigns"
        joinCampaignButton.setOnClickListener {
            // Cacher le TextView et afficher les campagnes publiques
            joinCampaignMessageTextView.visibility = View.GONE
            showPublicCampaignsFragment()  // Afficher le fragment des campagnes publiques
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