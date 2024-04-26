package com.msha.goal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msha.goal.viewmodel.MainViewModel
import com.msha.goal.R
import com.msha.goal.databinding.FragmentHomeBinding
import com.msha.goal.model.Goal
import com.msha.goal.utils.SwipeToRemove


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        val adapter = RecyclerAdapter {
            vm.selectHabit(it)
            findNavController().navigate(R.id.action_homeFragment_to_habitDetailsFragment)
            (activity as MainActivity).supportActionBar?.title = vm.selectedHabit.value?.name
        }

        binding.recyclerView.adapter = adapter
        val recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        vm.habitList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.addGoal.setOnClickListener {
            AddGoalFragment().show(requireActivity().supportFragmentManager, "Add goal")
        }

        fun deleteGoal (goal: Goal){
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){_,_ ->
                vm.deleteGoal(goal)
                Toast.makeText(requireContext(), "Your ${goal.name} goal with all the progress was deleted", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No"){ _, _ ->
            }
            builder.setTitle("Delete ${goal.name} goal ?")
            builder.setMessage("Are you sure you want to delete your goal? \n" +
                    "On click yes you confirm you want all your progress to be deleted")
            builder.create().show()
        }

        val callback = object : SwipeToRemove() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    vm.habitList.value?.get(position)?.let {
                        deleteGoal(it)
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }
        ItemTouchHelper(callback).apply {
            attachToRecyclerView(recyclerView)
        }

        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}