package com.msha.goal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msha.goal.model.Goal

class MainViewModel : ViewModel() {
    val habitList: MutableLiveData<List<Goal>> by lazy{
        MutableLiveData<List<Goal>>(listOf())
    }

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
        val goal = Goal(name,target = target)
        val updatedList = habitList.value?.toMutableList()
        updatedList?.add(goal)
        habitList.value = updatedList
    }
    fun getGoalByName(name: String): Goal? {
        return  habitList.value?.find {
            it.name == name
        }
    }
}