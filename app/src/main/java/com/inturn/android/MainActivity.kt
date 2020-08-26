package com.inturn.android

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inturn.android.factory.MainViewModelFactory
import com.inturn.android.recyclerview.WaitingListAdapter
import com.inturn.android.viewmodel.MainViewModel
import com.inturn.android.databinding.ActivityMainBinding
import com.inturn.android.widgets.CircleTimer
import androidx.lifecycle.ViewModelProviders
import com.inturn.android.model.WaitingData

class MainActivity : AppCompatActivity() {
    lateinit var mTimer: CircleTimer

    companion object {
        const val TAG = "KotlinActivity"
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private var mContactList: ArrayList<WaitingData> = arrayListOf()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        mRecyclerView = findViewById(R.id.waitinglist)

        viewModel.waitingDatas.observe(this, Observer {
            waitinglist ->
            mRecyclerView.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = WaitingListAdapter(waitinglist)
            }
        })

//        val adapter = WaitingListAdapter(mContactList)

//        viewModel.getTableData.observe(this, Observer { getTable ->
//            mContactList.add(getTable)
//            adapter.notifyDataSetChanged()
//        })

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

//        mRecyclerView.adapter = adapter
//        mRecyclerView.layoutManager = LinearLayoutManager(parent)

        /** example */
        mTimer = findViewById(R.id.timer)
        mTimer.setValue(600.0F)
        mTimer.Start()

        binding.mainViewModel = viewModel
        binding.setLifecycleOwner(this)

        /**add NewRestaurant example*/
//        val restaurant = Restaurant(null, "Saku", "1234 Richardson St, Vancouver", mutableListOf())
//        addNewRestaurant(restaurant,{},{})

        /**add new customer example*/
//        val userdata = Customer(firstName = "firstName", lastName = "lastName", email = "name2@test.com")
//        postCustomerService(userdata ,{}, {fail{it}})

        /**add waiting data to restaurant example*/
//        val userdata = Customer(firstName = "firstName", lastName = "lastName", email = "name2@test.com")
//        val waitingData = WaitingData(null, java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
//        ,WaitingStatus.wating, userdata, 3)
//        addQueueInRestaurant("-MBAe66mDPPlynlus4-e", waitingData, {}, {})

        /**get all restaurant example*/
//        getAllRestaurant({success(it)},{fail(it)})

        /**update waiting status*/
//        updateWaitingDataWaitingStatus("-MBAe66mDPPlynlus4-e", "-MBBM00tsewixNETcr0x", WaitingStatus.wating, {}, {})
    }

    /**when success call this function*/
//    fun success(cdata: DataSnapshot) {
//        cdata.child("wating").children.forEach{
//            mContactList.add(it.getValue(WaitingData::class.java)!!)
//        }
//        mRecyclerView.adapter?.notifyDataSetChanged()
//    }

    /**when fail call this function*/
//    fun fail(error : Any?){
//
//    }

}
