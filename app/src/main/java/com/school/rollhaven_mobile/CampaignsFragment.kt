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
import com.school.rollhaven_mobile.placeholder.PlaceholderContent
import com.school.rollhaven_mobile.repositories.Campaign
import com.school.rollhaven_mobile.repositories.IRollHavenRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 */
class CampaignsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyItemRecyclerViewAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_campaigns_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch user campaigns
        val userId = getUserId() // Fetch the logged-in user ID
        fetchCampaigns(userId)
    }
    private fun getUserId(): Int {
        return arguments?.getInt("USER_ID") ?: throw IllegalArgumentException("User ID not provided")
    }
    private fun fetchCampaigns(userId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5277/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IRollHavenRepository::class.java)
        val call: Call<List<Campaign>> = service.getCampaigns(userId)

        call.enqueue(object : Callback<List<Campaign>> {
            override fun onResponse(
                call: Call<List<Campaign>>,
                response: Response<List<Campaign>>
            ) {
                if (response.isSuccessful) {
                    val campaigns = response.body() ?: emptyList()
                    setupRecyclerView(campaigns)
                } else {
                    Log.e("CampaignsFragment", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                Log.e("CampaignsFragment", "Failure: ${t.message}", t)
            }
        })
    }


    private fun setupRecyclerView(campaigns: List<Campaign>) {
        adapter = MyItemRecyclerViewAdapter(campaigns)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}