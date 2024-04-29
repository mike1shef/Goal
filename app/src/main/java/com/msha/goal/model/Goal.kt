package com.msha.goal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("goals")
data class Goal (
    @PrimaryKey(autoGenerate = true)
    val gid: Long = 0L,
    @ColumnInfo(name = "goal_name")
    val name : String,
    @ColumnInfo("goal_progress")
    var progress : Double = 0.0,
    @ColumnInfo("goal_target")
    var target: Double,
    @ColumnInfo("goal_isCompleted")
    var isCompleted : Boolean = false,
    @ColumnInfo("goal_start_date")
    var startDate : Long = LocalDate.now().toEpochDay(),
    @ColumnInfo("goal_duration")
    var goalDuration : Int = 0,
    @ColumnInfo("goal_end")
    var goalEndDate : Long? = null,
)


/** Added minimal class to get from DB only what needed.
 *  To be implemented letter  https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1 4 point
data class GoalMinimal (
    val name: String,
    var progress: Double,
    var target: Double,
    var isCompleted: Boolean
)
*/