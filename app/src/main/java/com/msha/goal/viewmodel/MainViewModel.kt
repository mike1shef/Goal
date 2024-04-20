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
import kotlinx.coroutines.launch

class MainViewModel (private val repository: GoalRepository) : ViewModel() {

    val habitList : LiveData<List<Goal>> = repository.allGoals.asLiveData()

    val currentGoal : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

//    fun addProgress (progress : Double){
//        val goal = habitList.value?.first{
//            it.name == currentGoal.value
//        }!!
//        val newProgress = goal.progress.plus(progress)
//        val updatedGoal = goal.copy(
//            progress = newProgress
//        )
//        habitList.postValue(updatedGoal)
//    }

    fun addHabit(name : String, target: Double){
        viewModelScope.launch {
            repository.insertGoal(Goal(name = name, target = target))
        }
    }
//    fun addProgress (progress: Double) {
//
//        val measurement = Measurement(progress = progress)
//    }
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