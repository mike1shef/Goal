package com.msha.goal.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.msha.goal.model.Goal
import com.msha.goal.model.GoalDao
import com.msha.goal.model.Measurement
import kotlinx.coroutines.flow.Flow

class GoalRepository (private val goalDao: GoalDao) {

    val allGoals : Flow<List<Goal>> = goalDao.getAllGoals()

    @WorkerThread
    suspend fun insertGoal (goal: Goal){
        goalDao.insertGoal(goal)
    }

    @WorkerThread
    suspend fun insertMeasurement (measurement: Measurement){
        goalDao.insertMeasurement(measurement)
    }

    @WorkerThread
    suspend fun addMeasurement (goal: Goal, measurement: Measurement){
        goalDao.addMeasurement(goal, measurement)
    }

    @WorkerThread
    suspend fun getExactMeasurements (goal: Goal) : List<Measurement>{
        return goalDao.getExactMeasurements(goal.gid)
    }


    suspend fun deleteGoal (goal: Goal) {
        goalDao.deleteGoalWithMeasurements(goal)
    }
}