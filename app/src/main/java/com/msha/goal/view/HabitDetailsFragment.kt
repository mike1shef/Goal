package com.msha.goal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        val adapter = DetailsRecyclerAdapter()
        binding.detailsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        vm.selectedHabit.observe(viewLifecycleOwner, Observer {currentGoal ->
            val template = String.format(Locale.getDefault(),"%.1f of %.1f", currentGoal.progress, currentGoal.target)
            binding.currentGoalText.text = template
            binding.progressBar.progress = currentGoal.progress.toInt()
            binding.progressBar.max = currentGoal.target.toInt()

            binding.currentGoalText.setOnClickListener {
                binding.currentGoalText.text = changeView(currentGoal.progress, currentGoal.target)
            }

            if (currentGoal.isCompleted){
                binding.addMeasurement.hide()

                val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setIcon(R.drawable.outline_done_24)
                    .setTitle("You've reached your goal!")
                    .setCancelable(true)
                    .setNegativeButton("Add goal"){ dialog, position ->
                        requireActivity().supportFragmentManager.popBackStack()
                        findNavController().navigate(R.id.addGoalFragment)
                    }
                    .setPositiveButton("Add target"){dialog, position ->
                        vm.addTarget(100.0)

                    }
                    .setMessage("Select what to do next? Add a value to your current target or create a new goal")

                dialog.create().show()



            }

            if (vm.getMeasurements().isEmpty()) {
                binding.moreButton.visibility = View.GONE
            } else {
                binding.moreButton.visibility = View.VISIBLE
            }
        })

        binding.addMeasurement.setOnClickListener {

            findNavController().navigate(R.id.addMeasurementFragment)

        }

        binding.moreButton.setOnClickListener {
            val listOfMeasurements = vm.getMeasurements()
            adapter.submitList(listOfMeasurements)

            if (binding.moreButton.text == "more"){
                binding.detailsRecycler.visibility = View.VISIBLE
                binding.moreButton.text = "less"
            } else {
                binding.detailsRecycler.visibility = View.GONE
                binding.moreButton.text = "more"
            }
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
        binding.progressBar.progress = 0
        _binding = null
    }
}