package com.msha.goal.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.msha.goal.model.Goal
import com.msha.goal.model.Measurement

data class GoalWithMeasurements(
    @Embedded val goal: Goal,
    @Relation(
        parentColumn = "gid",
        entityColumn = "gid"
    )
    val measurements : List<Measurement>
)
