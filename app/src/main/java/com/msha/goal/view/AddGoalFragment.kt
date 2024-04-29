package com.msha.goal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.msha.goal.viewmodel.MainViewModel
import com.msha.goal.databinding.FragmentAddGoalBinding

class AddGoalFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentAddGoalBinding? = null
    private val binding get() = _binding!!
    private val vm : MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGoalBinding.inflate(inflater,container, false)
        val goalNameText = binding.editGoalName.editText
        val goalNumberText = binding.editTarget.editText
        val saveButton = binding.saveButton.apply {
            this.isEnabled = false
        }

        binding.durationPicker.apply {
            this.maxValue = 4
            this.minValue = 0
            displayedValues = vm.pickerVals
        }

        goalNameText?.doOnTextChanged { _, _, _, _ ->
            saveButton.isEnabled = validateInput(goalNameText, goalNumberText)
        }

        goalNumberText?.doOnTextChanged { _, _, _, _ ->
            saveButton.isEnabled = validateInput(goalNameText, goalNumberText)
        }

        binding.saveButton.setOnClickListener {
            val text= goalNameText?.text?.toString()
            val target = goalNumberText?.text.toString().toDoubleOrNull()
            val duration = binding.durationPicker.value

            if (target != null){
                vm.addHabit(text!!,target, duration)
            } else {
                Toast.makeText(context, "Enter target", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }


        return binding.root
    }

    private fun validateInput(goalNameEditText: EditText?, goalNumberEditText: EditText?): Boolean {
        val name = goalNameEditText?.text.toString()
        val target = goalNumberEditText?.text.toString().toDoubleOrNull()

        return name.isNotBlank() && target != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}