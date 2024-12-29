package com.school.rollhaven_mobile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.school.rollhaven_mobile.placeholder.PlaceholderContent.PlaceholderItem
import com.school.rollhaven_mobile.databinding.FragmentCampaignsBinding
import com.school.rollhaven_mobile.repositories.Campaign

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class MyItemRecyclerViewAdapter(
    private val campaigns: List<Campaign>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.CampaignViewHolder>() {

    class CampaignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvCampaignName)
        val description: TextView = view.findViewById(R.id.tvCampaignDescription)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_campaigns, parent, false)
        return CampaignViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        val campaign = campaigns[position]
        holder.title.text = campaign.name
        holder.description.text = campaign.description
    }
    override fun getItemCount(): Int = campaigns.size



}