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
            Goal("Run", target = 100.0),
            Goal("Cycle", target = 125.0),
        )

        val adapter = RecyclerAdapter(this)
        binding.recyclerView.adapter = adapter
        val recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        adapter.submitList(goalsList)

//        binding.progressBar.apply {
//            this.min = 0
//            this.max = 1
//        }

        val editText = binding.numbersMainEditText



        binding.floatingButton.setOnClickListener {
            if (editText.text.isNullOrBlank() || editText.text!!.equals("00")){
                Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show()
            } else {
                val number = editText.text.toString().toDouble()
                val itemToChange = goalsList.filter { it.isSelected }
                if (itemToChange.isNullOrEmpty()){
                    Toast.makeText(this, "You need to select your goal first", Toast.LENGTH_SHORT).show()
                    binding.numbersMainEditText.text?.clear()
                } else {
                    itemToChange[0].addProgress(number)
                    adapter.notifyDataSetChanged()
                    itemToChange[0].isSelected = false
                    adapter.notifyDataSetChanged()
                    binding.numbersMainEditText.text?.clear()
                }

            }

        }

        fun addProgress(float: Float, goal : Int) {

            val max = 100
            val min = 0
        }

        fun ifCompleted(goal : Int) {

        }



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}