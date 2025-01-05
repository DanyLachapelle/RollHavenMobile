package com.school.rollhaven_mobile


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.school.rollhaven_mobile.databinding.FragmentRollBinding
import kotlin.math.sqrt

class RollFragment : Fragment() , SensorEventListener{
    private var _binding: FragmentRollBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastAcceleration: Float = 0f
    private var currentAcceleration: Float = 0f
    private var shake: Float = 0f

    val typeRolls = listOf("Combat","Competence","Damage" ,"Other")
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

        // Initialisation des capteurs pour la détection de secousse
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        // Configuration des spinners
        val adapterTypeRoll = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            typeRolls
        )
        adapterTypeRoll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapterTypeRoll

        val adapterTypeAutre = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            typeAutres
        )
        adapterTypeAutre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAutre.adapter = adapterTypeAutre

        // Listeners pour les spinners
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.LinearLayoutAutre.visibility = View.VISIBLE

                typeAutres.clear()
                when (typeRolls[position]) {
                    "Combat" -> typeAutres.addAll(listOf("Sword","Axe","Bow","Dagger"))
                    "Competence" -> typeAutres.addAll(listOf("Acrobatics", "Animal Handling", "Arcana", "Athletics", "Deception", "History", "Insight", "Intimidation", "Investigation", "Medicine", "Nature", "Perception", "Performance", "Persuasion", "Religion", "Sleight of Hand", "Stealth", "Survival"))
                    "Damage" -> typeAutres.addAll(listOf("Sword","Axe","Bow","Dagger"))
                    "Other" -> typeAutres.addAll(listOf("d6", "d8", "d10", "d12", "d20"))
                }
                adapterTypeAutre.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerAutre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Placeholder
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    // Méthodes pour la détection de mouvement
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = currentAcceleration - lastAcceleration
            shake = shake * 0.9f + delta

            if (shake > 12) {
                onShakeDetected()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun onShakeDetected() {
        val type = binding.spinnerType.selectedItem
        val autre = binding.spinnerAutre.selectedItem

        val dice =calculDice(type.toString(), autre.toString())

        val randomNumber = (1..dice).random()

        Toast.makeText(
            requireContext(),
            "Résultat: $randomNumber + $type + $autre",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun calculDice(type: String, autre: String): Int {
        when (type) {
            "Combat" -> return 20
            "Competence" -> return 20
            "Damage" -> return when (autre) {
                "Sword" -> 10
                "Axe" -> 12
                "Bow" -> 6
                "Dagger" -> 4
                else -> {

                    -1
                }
            }
            "Other" -> return when (autre) {
                "6" -> 6
                "8" -> 8
                "10" -> 10
                "12" -> 12
                "20" -> 20
                else -> {

                    -1
                }
            }
            else -> {

                return -1
            }
        }
        return -1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sensorManager.unregisterListener(this)
        _binding = null
    }

    companion object {
        fun newInstance() =
            RollFragment().apply {}
    }
}
