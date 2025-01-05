package com.school.rollhaven_mobile

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

    val typeRolls = listOf("Combat", "Magie", "Competence", "Autre")
    val typeAutres: MutableList<String> = mutableListOf()

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
        val adapterTypeRoll = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            typeRolls
        )
        adapterTypeRoll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerType.adapter = adapterTypeRoll

        // Adapter initial pour spinnerAutre
        val adapterTypeAutre = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            typeAutres
        )
        adapterTypeAutre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAutre.adapter = adapterTypeAutre

        // Listener pour spinnerType
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Afficher LinearLayoutAutre
                binding.LinearLayoutAutre.visibility = View.VISIBLE

                // Mettre à jour la liste typeAutres en fonction de la sélection
                typeAutres.clear()
                when (typeRolls[position]) {
                    "Combat" -> typeAutres.addAll(listOf("Epee", "Hache", "Arc"))
                    "Magie" -> typeAutres.addAll(listOf("Feu", "Glace", "Vent"))
                    "Competence" -> typeAutres.addAll(listOf("Escalade", "Intimidation", "Histoire"))
                    "Autre" -> typeAutres.addAll(listOf("d6", "D8", "d10", "d12", "d20"))
                }

                // Notifier l'adaptateur du changement dans la liste
                adapterTypeAutre.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Pas d'action spécifique requise
            }
        }

        // Listener pour spinnerAutre
        binding.spinnerAutre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Exemple : Afficher un message avec l'option sélectionnée

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Pas d'action spécifique requise
            }
        }
        setUpListener()
    }

    private fun setUpListener() {
        binding.BRoll.setOnClickListener {
            val type = binding.spinnerAutre.selectedItem

            Toast.makeText(
                requireContext(),
                "type sélectionné : $type",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            RollFragment().apply {
                // Ajoutez des paramètres si nécessaire
            }
    }
}
