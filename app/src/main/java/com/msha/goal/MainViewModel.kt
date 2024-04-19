package com.msha.goal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val habitList: MutableLiveData<List<Goal>> by lazy{
        MutableLiveData<List<Goal>>(mutableListOf())
    }

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