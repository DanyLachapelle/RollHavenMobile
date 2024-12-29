package com.school.rollhaven_mobile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.school.rollhaven_mobile.placeholder.PlaceholderContent.PlaceholderItem
import com.school.rollhaven_mobile.databinding.FragmentCampaignPublicBinding
import com.school.rollhaven_mobile.repositories.Campaign

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CampaignPublicFragmentRecyclerViewAdapter(
    private var campaigns: List<Campaign>
) : RecyclerView.Adapter<CampaignPublicFragmentRecyclerViewAdapter.CampaignViewHolder>() {

    // ViewHolder pour chaque campagne
    class CampaignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_number) // Assurez-vous que ces IDs sont corrects dans item_campaign.xml
        val description: TextView = view.findViewById(R.id.content)
    }

    // Créer une vue pour chaque élément de la liste (campagne)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_campaign_public, parent, false) // Assurez-vous que le fichier item_campaign.xml existe
        return CampaignViewHolder(view)
    }

    // Lier les données de la campagne à la vue correspondante
    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        val campaign = campaigns[position]
        holder.title.text = campaign.name
        holder.description.text = campaign.description
    }

    // Nombre d'éléments dans la liste des campagnes
    override fun getItemCount(): Int = campaigns.size

    fun submitList(campaignList: List<Campaign>) {
        campaigns = campaignList
        notifyDataSetChanged()  // Met à jour l'affichage
    }
}