package com.school.rollhaven_mobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.school.rollhaven_mobile.databinding.ActivityCampaignBinding
import com.school.rollhaven_mobile.databinding.FragmentRollBinding

class ActivityCampaign : AppCompatActivity() {
    lateinit var binding: ActivityCampaignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpListener()
    }

    private fun setUpListener() {
        binding.buttonDice.setOnClickListener {

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView2,
                    RollFragment.newInstance(),
                    "currentFragment"
                )
                .commit()
        }
    }
}