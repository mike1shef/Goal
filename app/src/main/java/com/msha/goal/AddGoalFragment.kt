package com.msha.goal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.msha.goal.databinding.FragmentAddGoalBinding

class AddGoalFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentAddGoalBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGoalBinding.inflate(inflater,container, false)
        val vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val goalNameText = binding.editGoalName.editText
        val goalNumberText = binding.editTargetValue.editText
        val saveButton = binding.saveButton.apply {
            this.isEnabled = false
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

            if (target != null){
                vm.addHabit(text!!,target)
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