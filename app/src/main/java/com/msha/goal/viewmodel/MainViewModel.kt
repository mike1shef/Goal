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

class MainViewModel (private val repository: GoalRepository) : ViewModel() {

    val habitList : LiveData<List<Goal>> = repository.allGoals.asLiveData()

    private val mutableSelectedHabit : MutableLiveData<Goal>  =  MutableLiveData<Goal>()
    val selectedHabit : MutableLiveData<Goal> get() = mutableSelectedHabit

    fun selectHabit (goal: Goal){
        mutableSelectedHabit.value = goal
    }

    fun addHabit(name : String, target: Double){
        viewModelScope.launch {
            repository.insertGoal(Goal(name = name, target = target))
        }
    }
    fun addProgress (progress: Double) {
        val goal = habitList.value?.find { it == selectedHabit.value }!!
        goal.progress += progress

        if (goal.progress >= goal.target) {
            goal.isCompleted = true
        }

        viewModelScope.launch {
            val measurement = Measurement(gid = goal.gid, progress = progress)
            repository.addMeasurement(goal, measurement)
        }

        selectedHabit.value = goal
    }

    fun getMeasurements (){
        viewModelScope.launch {
            repository.getExactMeasurements(selectedHabit.value!!)
        }
    }

    fun deleteGoal (goal: Goal){
        if (goal == mutableSelectedHabit.value){
            mutableSelectedHabit.value = Goal(name = "", target = 0.0)
        }

        viewModelScope.launch(Dispatchers.IO){
            repository.deleteGoal(goal)
        }
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