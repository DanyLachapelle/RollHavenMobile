package com.school.rollhaven_mobile

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.school.rollhaven_mobile.databinding.FragmentRollBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RollFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RollFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentRollBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure les adaptateurs des spinners
        val typeRolls = listOf("Combat", "Magie", "Competence", "Autre")
        val typeAutres : MutableList<String> = mutableListOf()
        val adapterTypeRoll = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            typeRolls
        )
        adapterTypeRoll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerType.adapter = adapterTypeRoll

        binding.spinnerType.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(typeRolls[position]=="Combat"){
                    typeAutres.add("Epee")
                    typeAutres.add("Hache")
                    typeAutres.add("Arc")
                }else if (typeRolls[position]=="Magie"){
                    typeAutres.add("Feu")
                    typeAutres.add("Glace")
                    typeAutres.add("Vent")
                }else if (typeRolls[position]=="Competence"){
                    typeAutres.add("Escalade")
                    typeAutres.add("Intimidation")
                    typeAutres.add("Histoire")
                }
                /*Toast.makeText(requireContext(),"item is ${typeRolls[position]}", Toast.LENGTH_LONG).show()*/
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        val adapterTypeAutre = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            typeAutres
        )
        adapterTypeRoll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerAutre.adapter = adapterTypeAutre
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}