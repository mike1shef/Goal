package com.msha.goal.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.msha.goal.R
import com.msha.goal.databinding.FragmentAddGoalBinding
import com.msha.goal.databinding.FragmentAddMeasurementBinding
import com.msha.goal.viewmodel.MainViewModel

class AddMeasurementFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentAddMeasurementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMeasurementBinding.inflate(inflater, container, false)
        val vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val measurementText = binding.addMeasurementValue.editText
        val addMeasurementButton = binding.addMeasurementButton

        measurementText?.doOnTextChanged { _, _, _, _ ->
            addMeasurementButton.isEnabled = (measurementText.text.toString().toDoubleOrNull() != null)
        }


        binding.addMeasurementButton.setOnClickListener {
            val progress = measurementText?.text.toString().toDoubleOrNull()

            if (progress != null) {
                vm.addProgress(progress)
                Toast.makeText(context, "Measurement added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Enter progress", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}

