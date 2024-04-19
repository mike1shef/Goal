package com.msha.goal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.msha.goal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setSupportActionBar(binding.toolbar)
        setContentView(view)

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


        binding.floatingButton.setOnClickListener {
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show()
        }
    }
}