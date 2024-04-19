package com.msha.goal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        val vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val adapter = RecyclerAdapter() {
            vm.currentGoal.postValue(it.name)
            findNavController().navigate(R.id.action_homeFragment_to_habitDetailsFragment)
        }

        binding.recyclerView.adapter = adapter
        val recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        vm.habitList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}