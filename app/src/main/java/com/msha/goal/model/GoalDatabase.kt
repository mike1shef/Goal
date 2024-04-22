package com.msha.goal.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(
    entities = [Goal::class, Measurement::class],
    version = 1
        )

abstract class GoalDatabase : RoomDatabase() {
    abstract val goalDao : GoalDao
    companion object {
        @Volatile
        private var INSTANCE : GoalDatabase? = null
        fun getInstance(context: Context) : GoalDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    GoalDatabase::class.java,
                    "goal_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}