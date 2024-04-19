package com.msha.goal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msha.goal.databinding.FragmentHabbitDetailsFragmentBinding
import java.util.Locale

class HabitDetailsFragment : Fragment() {

    private var _binding: FragmentHabbitDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentGoal: Goal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabbitDetailsFragmentBinding.inflate(inflater, container, false)
        val vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]




        vm.habitList.observe(viewLifecycleOwner, Observer {
            val list = it

            vm.currentGoal.observe(viewLifecycleOwner, Observer {
                currentGoal = list.first { goal ->
                    goal.name == it
                }
                val template = String.format(Locale.getDefault(),"%.2f of %.2f", currentGoal.progress, currentGoal.target)
                binding.currentGoalText.text = template
                binding.progressBar.progress = currentGoal.progress.toInt()
                binding.progressBar.max = currentGoal.target.toInt()
            })
        })
        binding.addEasurement.setOnClickListener {
            currentGoal.addProgress(10.0)
        }


        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}