package com.inturn.android.model

data class Restaurant(
    var id : String? = null,
    var name : String? = null,
    var address: String? = null,
    var waiting : MutableList<WaitingData> = mutableListOf()
)

data class getRestaurant(
    var id : String? = null,
    var name : String? = null,
    var address: String? = null,
    var waitingList : MutableList<WaitingData> = mutableListOf()
)