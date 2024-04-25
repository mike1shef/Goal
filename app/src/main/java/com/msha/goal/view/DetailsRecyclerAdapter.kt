package com.msha.goal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msha.goal.model.Goal
import com.msha.goal.R
import com.msha.goal.model.Measurement
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailsRecyclerAdapter()
    : ListAdapter<Measurement, DetailsRecyclerAdapter.MeasurementViewHolder>(MeasurementDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        val holder = MeasurementViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.details_item_recycler, parent,false))

        return holder
    }

    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        val item = getItem(position)

        holder.date.text = convertLongToDate(item.date)
        holder.text.text = "${item.progress} km"

    }


    fun convertLongToDate (date : Long) : String{
        val format : DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM, d")
        val updatedDate = LocalDate.ofEpochDay(date)
        return updatedDate.format(format)
    }

    class MeasurementViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val date: TextView = view.findViewById(R.id.details_view_item_date)
        val text: TextView = view.findViewById(R.id.details_view_item_measurement)

    }
}
class MeasurementDiffUtilCallback : DiffUtil.ItemCallback<Measurement>(){
    override fun areItemsTheSame(oldItem: Measurement, newItem: Measurement): Boolean
            = (oldItem.mid == newItem.mid)

    override fun areContentsTheSame(oldItem: Measurement, newItem: Measurement): Boolean = (oldItem == newItem)

    override fun getChangePayload(oldItem: Measurement, newItem: Measurement): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}

