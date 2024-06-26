package com.msha.goal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msha.goal.model.Goal
import com.msha.goal.R

class RecyclerAdapter(val onClick : (goal : Goal) -> Unit)
    : ListAdapter<Goal, RecyclerAdapter.GoalViewHolder>(GoalDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val holder = GoalViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent,false))

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION){
                val item = getItem(holder.adapterPosition)
                onClick(item)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val item = getItem(position)
        holder.text.text = item.name
        holder.progressBar.apply {
            this.max = item.target.toInt()
            this.min = 0

            this.progress = item.progress.toInt()
        }

        holder.imageComplete.visibility = if (item.isCompleted) View.VISIBLE else View.GONE
    }

    class GoalViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.recycler_view_item_text)
        val progressBar: ProgressBar = view.findViewById(R.id.recycler_view_item_progress_bar)
        val imageComplete : ImageView = view.findViewById(R.id.image_on_card)
    }
}
class GoalDiffUtilCallback : DiffUtil.ItemCallback<Goal>(){
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean
            = (oldItem.name == newItem.name)

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean = (oldItem == newItem)

    override fun getChangePayload(oldItem: Goal, newItem: Goal): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}

