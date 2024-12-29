package com.school.rollhaven_mobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.school.rollhaven_mobile.placeholder.PlaceholderContent
import com.school.rollhaven_mobile.repositories.IRollHavenRepository
import com.school.rollhaven_mobile.utils.RetrofitFactory
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class CampaignPublicFragment : Fragment() {

    private lateinit var campaignsAdapter: CampaignPublicFragmentRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater le layout
        val view = inflater.inflate(R.layout.fragment_campaign_public_list, container, false)

        campaignsAdapter = CampaignPublicFragmentRecyclerViewAdapter(emptyList())
        // Lier RecyclerView et Adapter
        recyclerView = view.findViewById(R.id.list)  // RecyclerView du layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())  // Utilisez un LinearLayoutManager
        recyclerView.adapter = campaignsAdapter  // Associez l'adaptateur au RecyclerView

        // Charger les campagnes publiques
        loadPublicCampaigns()

        return view
    }

    private fun loadPublicCampaigns() {
        // Utilisation de Retrofit pour obtenir les campagnes publiques
        lifecycleScope.launch {
            try {
                // Appel API pour récupérer les campagnes publiques
                val response = RetrofitFactory.retrofit.create(IRollHavenRepository::class.java).getPublicCampaigns()

                // Vérifier que la réponse est réussie
                if (response.isNotEmpty()) {
                    // Récupérer le corps de la réponse, supposant qu'il s'agit d'une liste de campagnes
                    campaignsAdapter.submitList(response)
                } else {
                    Log.e("CampaignPublicFragment", "Aucune campagne publique disponible")
                }
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("PublicCampaignsFragment", "Error loading public campaigns", e)
            }
        }
    }

}