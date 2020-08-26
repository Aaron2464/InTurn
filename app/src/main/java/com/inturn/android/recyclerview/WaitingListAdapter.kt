package com.inturn.android.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.inturn.android.model.WaitingData
import com.inturn.android.R
import com.inturn.android.databinding.LayoutRecyclerViewItemBinding
import com.inturn.android.enums.WaitingStatus
import com.inturn.android.services.updateWaitingDataWaitingStatus

class WaitingListAdapter(private val waitingList: ArrayList<WaitingData>): RecyclerView.Adapter<WaitingListAdapter.WaitingDataViewHolder>() {

    override fun getItemCount() = waitingList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitingDataViewHolder {
        return WaitingDataViewHolder(
            DataBindingUtil.inflate<LayoutRecyclerViewItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_recycler_view_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WaitingDataViewHolder, position: Int) {
        holder.recyclerviewWaitingData.waitingdata = waitingList[position]
        holder.recyclerviewWaitingData.checkButton.setOnClickListener {
            ///TODO Here should show a password dialog
            ///TODO After login function finish should use different restaurantId
            updateWaitingDataWaitingStatus("-MBAe66mDPPlynlus4-e", waitingList[position].id!!, WaitingStatus.checked, ::success, ::error)
        }

        holder.recyclerviewWaitingData.cancelButton.setOnClickListener {
            ///TODO Here should show a password dialog
            ///TODO After login function finish should use different restaurantId
            updateWaitingDataWaitingStatus("-MBAe66mDPPlynlus4-e", waitingList[position].id!!, WaitingStatus.cancle, ::success, ::error)
        }
    }

    inner class WaitingDataViewHolder(val recyclerviewWaitingData: LayoutRecyclerViewItemBinding): RecyclerView.ViewHolder(recyclerviewWaitingData.root)

    fun error(errormessage : Any?){
        ///TODO If error happend should show error message tell user there is some thing wrong so couldn't cancel or checked

    }

    /**when success call this function*/
    fun success(cdata: DataSnapshot, waitingId:String) {
        var watingD = cdata.getValue(WaitingStatus::class.java)!!
        waitingList.find{ it.id == waitingId }?.status = watingD
        notifyDataSetChanged()
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WLViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view_item, parent, false)
//        return WLViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int = waitingList.size
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onBindViewHolder(holder: WLViewHolder, position: Int) {
//
//
//
//
//        holder.bind(waitingList[position])
//    }
//
//    inner class WLViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        private val firstNameTextView: TextView = itemView.findViewById(R.id.firstNameTextView)
//        private val lastNameTextView: TextView = itemView.findViewById(R.id.lastNameTextView)
//
//        private val checkTimeTextView: TextView = itemView.findViewById(R.id.checkTimeTextView)
//        private val expectedTimeTextView: TextView = itemView.findViewById(R.id.expectedTimeTextView)
////        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
////        private val customerTextView: TextView = itemView.findViewById(R.id.customerTextView)
//        private val peopleTextView: TextView = itemView.findViewById(R.id.peopleTextView)
//
//        @RequiresApi(Build.VERSION_CODES.O)
//        fun bind(item: WaitingData) {
//            firstNameTextView.text = item.customer?.firstName
//            lastNameTextView.text = item.customer?.lastName
//            checkTimeTextView.text = "${item.checkTime?.hours}:${item.checkTime?.minutes}"
//            expectedTimeTextView.text =  "${item.expectedTime?.hours}:${item.expectedTime?.minutes}"
////            statusTextView.text = item.status.toString()
//            peopleTextView.text = item.people.toString()
//        }
//    }
}
