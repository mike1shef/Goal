package com.msha.goal.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.msha.goal.model.relations.GoalWithMeasurements
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals ORDER BY goal_progress DESC")
    fun getAllGoalsOrdered(): Flow<List<Goal>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGoal (goal: Goal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeasurement (measurement: Measurement)

//    @Transaction
//    @Query("SELECT * FROM measurements WHERE gid = :goalId ")
//    suspend fun getGoalwithMeasurements(goalId : Long) : List<GoalWithMeasurements>

    @Query("DELETE FROM goals")
    suspend fun deleteAll()

}