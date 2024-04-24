package com.msha.goal.view

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.msha.goal.R
import com.msha.goal.databinding.FragmentAddMeasurementBinding
import com.msha.goal.viewmodel.MainViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddMeasurementFragment : Fragment() {
    private var _binding : FragmentAddMeasurementBinding? = null
    private val binding get() = _binding!!
    private val vm : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMeasurementBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.hide()

        val measurementText = binding.editTextBox.editText
        val saveButton = binding.saveButton

        binding.currentGoal.apply {
            this.text = vm.selectedHabit.value?.name
        }

        var providedDate = LocalDate.now().toEpochDay()
        val currentDateField = binding.currentDate


        currentDateField.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val oldCalendar = Calendar.Builder().setDate(year, month -1, day).build()

            val constraints : CalendarConstraints = CalendarConstraints.Builder()
                .setFirstDayOfWeek(1)
                .setStart(oldCalendar.timeInMillis)
                .setEnd(calendar.timeInMillis)
                .setOpenAt(calendar.timeInMillis)
                .build()

            val datePickerDialog = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraints)
                .build()
            datePickerDialog.show((activity as MainActivity).supportFragmentManager,"DatePicker")

            datePickerDialog.addOnPositiveButtonClickListener { selectedDate ->
                val date = Date(selectedDate)
                val selectedLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toEpochDay()

                val today = LocalDate.now()
                val yesterday = LocalDate.of(today.year, today.month, today.dayOfMonth - 1).toEpochDay()

                when (selectedLocalDate) {
                    today.toEpochDay() -> binding.currentDate.text = "Today"
                    yesterday -> {
                        providedDate = selectedLocalDate
                        binding.currentDate.text = "Yesterday"
                    }
                    else -> {
                        val sdf = SimpleDateFormat("EEE MMM, d", Locale.getDefault())
                        val dateToDisplay = sdf.format(date)
                        providedDate = selectedLocalDate
                        binding.currentDate.text = dateToDisplay
                    }
                }
                Log.i("LocalDate", "Provided date to save in DB: $providedDate. Selected date converted: $selectedLocalDate")
            }
        }

        val closeButton = binding.closeButton

        measurementText?.doOnTextChanged { _, _, _, _ ->
            saveButton.isEnabled = (measurementText.text.toString().toDoubleOrNull() != null)
        }

        closeButton.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }

        saveButton.setOnClickListener {
            val progress = measurementText?.text.toString().toDoubleOrNull()

            if (progress != null) {
                vm.addProgress(progress,providedDate)
                Toast.makeText(context, "Measurement added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Enter progress", Toast.LENGTH_SHORT).show()
            }
            binding.root.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).supportActionBar?.show()
        _binding = null
    }
}

