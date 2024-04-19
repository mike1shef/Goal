package com.msha.goal

data class Goal (
    //val id: Long,
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

    fun showProgressLeft () : Double {
        return this.target - this.progress
    }
}
