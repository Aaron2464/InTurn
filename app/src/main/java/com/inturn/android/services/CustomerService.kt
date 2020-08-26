package com.inturn.android.services

import com.google.firebase.database.DataSnapshot
import com.inturn.android.model.Customer

fun getCustomerByEmailService(email:String, success:(customer : DataSnapshot)->Unit, error:(error:Any?)->Unit){
    getData("customer", success, error)

//    getData("customer",{
//        val restaurants: MutableList<Customer> = mutableListOf()
//        var customer : Customer? = null
//        it.children.forEach{
//            if (it.child("email").getValue(String::class.java).equals(email)) {
//                customer = it.getValue(Customer::class.java)!!
//                customer?.id = it.key
//                return@forEach
//            }
//        }
//
//        /**if that customer data not exist here will pass null*/
//        success(customer)
//    },{
//        error(error)
//    })
}

fun postCustomerService(customerData: Customer, success:(customer : DataSnapshot)->Unit, error:(error:Any?)->Unit){
    /**try to get first if is null post if not return that customer data*/
    getData("customer",{
        val restaurants: MutableList<Customer> = mutableListOf()
        var customer : DataSnapshot? = null
        it.children.forEach{
            if (it.child("email").getValue(String::class.java).equals(customerData.email)) {
                customer = it
                return@forEach
            }
        }

        /**if already exist don't post*/
        if(customer == null){
            postData("customer", customerData,
                success,
                {
                    error(error)
                })
        }else{
            success(customer!!)
        }

    }, error)
}