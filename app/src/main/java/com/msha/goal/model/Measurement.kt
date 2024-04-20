package com.msha.goal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true)
    val mid : Long = 0L,
    @ColumnInfo("gid")
    val gid : Long,
    @ColumnInfo("measurementProgress")
    val progress : Double,
    @ColumnInfo("measurementDate")
    val date : Long = LocalDate.now().toEpochDay()
)

/** Added minimal class to get from DB only what needed.
 *  To be implemented letter  https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1 4 point
 *
 *  data class MeasurementMinimal(
 *    val progress : Double,
 *     val date : Long = LocalDate.now().toEpochDay()
 * )
 */
