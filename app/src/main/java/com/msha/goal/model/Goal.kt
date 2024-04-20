package com.msha.goal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Goal (

    //val gid: Long = 0,
    val name : String,
    var progress : Double = 0.0,
    var target: Double,
    var isCompleted : Boolean = false,
){
    fun addProgress (double: Double){
        if (!this.isCompleted){
            val tempResult = progress + double
            if (tempResult >= this.target) {
                this.progress = this.target
                completeTheGoal()
            } else {
                this.progress += double
            }
        }
    }

    private fun completeTheGoal () {
        this.isCompleted = true
    }
}
