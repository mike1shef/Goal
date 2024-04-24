package com.msha.goal.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals")
    fun getAllGoals(): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGoal (goal: Goal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeasurement (measurement: Measurement)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update (goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)
    @Delete
    suspend fun deleteMeasurement (measurement: Measurement)
    @Query("DELETE FROM goals")
    suspend fun deleteAllGoals()

    @Query("DELETE FROM measurements")
    suspend fun deleteAllMeasurements()

    @Query("DELETE FROM measurements WHERE gid = :gid")
    suspend fun deleteMeasurementOfConcreteUser (gid : Long)


@Transaction
suspend fun deleteGoalWithMeasurements(goal: Goal) {
    deleteMeasurementOfConcreteUser(goal.gid)
    deleteGoal(goal)
}


    @Transaction
    suspend fun addMeasurement (currentGoal: Goal, measurement: Measurement ) {
        update(currentGoal)
        insertMeasurement(measurement)
    }

    @Query("SELECT * FROM measurements WHERE gid = :gid")
    suspend fun getExactMeasurements(gid : Long) : List<Measurement>

    @Query("DELETE FROM goals")
    suspend fun deleteAll()

}