package com.msha.goal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.msha.goal.databinding.FragmentHabbitDetailsFragmentBinding
import com.msha.goal.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)

        val goalsList = listOf(
            Goal("Running", target = 100.0, progress = 48.0),
            Goal("Cycling", target = 125.0),
        )

        val adapter = RecyclerAdapter() {

        }

        binding.recyclerView.adapter = adapter
        val recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        adapter.submitList(goalsList)




        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}