package com.msha.goal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.msha.goal.R
import com.msha.goal.viewmodel.MainViewModel
import com.msha.goal.databinding.FragmentHabbitDetailsFragmentBinding
import java.util.Locale

class HabitDetailsFragment : Fragment() {

    private var _binding: FragmentHabbitDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private var isPercentageView = false
    private val vm : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabbitDetailsFragmentBinding.inflate(inflater, container, false)

        vm.selectedHabit.observe(viewLifecycleOwner, Observer {currentGoal ->
            val template = String.format(Locale.getDefault(),"%.2f of %.2f", currentGoal.progress, currentGoal.target)
            binding.currentGoalText.text = template
            binding.progressBar.progress = currentGoal.progress.toInt()
            binding.progressBar.max = currentGoal.target.toInt()

            binding.currentGoalText.setOnClickListener {
                binding.currentGoalText.text = changeView(currentGoal.progress, currentGoal.target)
            }

            if (currentGoal.isCompleted){
                binding.addMeasurement.hide()
            }
        })

        binding.addMeasurement.setOnClickListener {
            findNavController().navigate(R.id.addMeasurementFragment)
        }

        return binding.root
    }


    private fun changeView (progress: Double, target:Double) : String {
        isPercentageView = !isPercentageView
        return if (isPercentageView) { "${((progress / target)*100).toInt()} %" }
        else { String.format(Locale.getDefault(),"%.2f of %.2f", progress, target) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}