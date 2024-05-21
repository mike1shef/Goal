package com.msha.goal.view

import android.animation.ValueAnimator
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
import java.time.LocalDate
import java.time.temporal.ChronoUnit
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

            animateProgressBar(currentGoal.progress)

            binding.currentGoalText.text = template
            binding.progressBar.max = currentGoal.target.toInt()

            if (currentGoal.goalEndDate != null) {
                binding.endCard.visibility = View.VISIBLE
                val endDate = LocalDate.ofEpochDay(currentGoal.goalEndDate!!)
                binding.cardDaysLeftValue.text = ChronoUnit.DAYS.between(LocalDate.now(), endDate).toString()
                }

            binding.currentGoalText.setOnClickListener {
                binding.currentGoalText.text = changeView(currentGoal.progress, currentGoal.target)
            }

            if (currentGoal.isCompleted){
                binding.addMeasurement.setOnClickListener {
                    vm.addTarget(10.0)
                }

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
        })

        binding.addMeasurement.setOnClickListener {
            findNavController().navigate(R.id.addMeasurementFragment)
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.back_action -> {
                    findNavController().popBackStack()

                    true
                }

                R.id.share_progress -> {

                    true
                }

                R.id.view_measurements -> {
                    val listOfMeasurements = vm.getMeasurements()
                    adapter.submitList(listOfMeasurements)

                    true
                }

                else -> false
            }
        }

        return binding.root
    }

    private fun animateProgressBar(progress: Double,) {
        val animator = ValueAnimator.ofInt(0, progress.toInt()).apply {
            this.duration = 300
            interpolator = android.view.animation.LinearInterpolator()
        }
        animator.addUpdateListener {animation ->
            binding.progressBar.progress = animation.animatedValue as Int
        }
        animator.start()
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