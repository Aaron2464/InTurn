package com.inturn.android.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.inturn.android.enums.WaitingStatus
import com.inturn.android.model.Customer
import com.inturn.android.model.WaitingData
import com.inturn.android.services.addQueueInRestaurant
import com.inturn.android.services.dataChange
import com.inturn.android.services.getRestaurant
import com.inturn.android.services.postCustomerService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class MainViewModel : ViewModel() {

    private val _getTableData = MutableLiveData<WaitingData>()
    val getTableData: LiveData<WaitingData>
        get() = _getTableData


    private val _firstName = MutableLiveData<String>()

    val firstName: MutableLiveData<String>
        get() = _firstName


    private val _lastName = MutableLiveData<String>()

    val lastName: MutableLiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()

    val email: MutableLiveData<String>
        get() = _email


    private val _people = MutableLiveData<String>()

    val people: MutableLiveData<String>
        get() = _people

    private val _isgetTableBtnEnable = MutableLiveData<Boolean>()
    val isgetTableBtnEnable: LiveData<Boolean>
        get() = _isgetTableBtnEnable

    private val _waitingDatas = MutableLiveData<ArrayList<WaitingData>>()
    val waitingDatas :MutableLiveData<ArrayList<WaitingData>>
        get() = _waitingDatas

    init {
        _isgetTableBtnEnable.value = true
        _firstName.value = ""
        _lastName.value = ""
        _email.value = ""
        _people.value=""
        _waitingDatas.value = arrayListOf()
        getRestaurant("-MBAe66mDPPlynlus4-e", ::success, ::error)
        dataChange("restaurant/-MBAe66mDPPlynlus4-e/waiting", ::success, ::error)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onGetTable() {
        if (isAllow()) {
            createCustomer()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createCustomer(){
        val userdata = Customer(firstName = firstName.value, lastName = lastName.value, email = email.value)
        postCustomerService(userdata , ::createWaitlist, ::error)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createWaitlist(userdata : DataSnapshot){

        val waitingData = WaitingData(null, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        , WaitingStatus.waiting, userdata.getValue(Customer::class.java)!!, people.value?.toInt())

        /**fix restaurantId just test for now*/
        ///TODO After restaurant login function finish here should use different restaurantId for different restaurant
        addQueueInRestaurant("-MBAe66mDPPlynlus4-e", waitingData, ::getTableSuccess, ::error)
    }

    fun getTableSuccess(restaurant: DataSnapshot){
//        _isgetTableBtnEnable.value = false
        _firstName.value = ""
        _lastName.value = ""
        _email.value = ""
        _people.value = ""

        /**Here had set observe so when _getTableData.value change will do add to recycleView*/
//        _getTableData.value = restaurant.getValue(WaitingData::class.java)!!
        var watingD = restaurant.getValue(WaitingData::class.java)!!
        watingD.id = restaurant.key
        _waitingDatas.value?.add(watingD)
        _waitingDatas.value = _waitingDatas.value
    }

    fun isAllow() : Boolean {
        //TODO: must have people value
        //TODO: must have legal email
        //TODO: must have first name or last name
        return true
    }

    fun error(errormessage : Any?){

    }

    /**when success call this function*/
    fun success(cdata: DataSnapshot) {
        cdata.child("waiting").children.forEach{
            var watingD = it.getValue(WaitingData::class.java)!!
            watingD.id = it.key
            _waitingDatas.value?.add(watingD)
        }
        _waitingDatas.value = _waitingDatas.value
    }
}