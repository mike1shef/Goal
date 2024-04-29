package com.msha.goal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.msha.goal.model.Goal
import com.msha.goal.model.Measurement
import com.msha.goal.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class MainViewModel (private val repository: GoalRepository) : ViewModel() {

    val habitList : LiveData<List<Goal>> = repository.allGoals.asLiveData()

    private val mutableSelectedHabit : MutableLiveData<Goal>  =  MutableLiveData<Goal>()
    val selectedHabit : MutableLiveData<Goal> get() = mutableSelectedHabit
    fun getMeasurements() : List<Measurement>{
        return runBlocking {
            repository.getExactMeasurements(selectedHabit.value!!)
        }
    }



    fun selectHabit (goal: Goal){
        mutableSelectedHabit.value = goal
    }



    fun addHabit(name : String, target: Double, duration : Int){
        val goalEndDate = calculateEndDate(duration)
        val newGoal = Goal(name = name, target = target, goalDuration = duration, goalEndDate = goalEndDate)

        viewModelScope.launch {
            repository.insertGoal(newGoal)
        }
    }
    fun addProgress (progress: Double, date: Long) {
        val goal = habitList.value?.find { it == selectedHabit.value }!!
        goal.progress += progress

        if (goal.progress >= goal.target) {
            goal.isCompleted = true
        }


        viewModelScope.launch {
            val measurement = Measurement(gid = goal.gid, progress = progress, date = date)
            repository.addMeasurement(goal, measurement)
        }

        selectedHabit.value = goal
    }

    fun addTarget (target: Double) {
        val goal = habitList.value?.find { it == selectedHabit.value }!!
        goal.target += target

        if (goal.progress < goal.target) {
            goal.isCompleted = false
        }
        viewModelScope.launch {
            repository.updateGoal(goal)
        }
        selectedHabit.value = goal
    }

    fun deleteGoal (goal: Goal){
        if (goal == mutableSelectedHabit.value){
            mutableSelectedHabit.value = Goal(name = "", target = 0.0)
        }

        viewModelScope.launch(Dispatchers.IO){
            repository.deleteGoal(goal)
        }
    }

    val pickerVals = arrayOf("no end date", "1 month", "3 months", " 6 months", "year")

    private fun calculateEndDate (duration: Int) : Long? {
        var endDate : Long? = null
        when (duration){
            1 -> endDate = LocalDate.now().plusMonths(1).toEpochDay()
            3 -> endDate = LocalDate.now().plusMonths(3).toEpochDay()
            6 -> endDate = LocalDate.now().plusMonths(6).toEpochDay()
            12 -> endDate = LocalDate.now().plusMonths(12).toEpochDay()
            else -> {}
        }
        return endDate
    }
}

class MainViewModelFactory(private val repository: GoalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}