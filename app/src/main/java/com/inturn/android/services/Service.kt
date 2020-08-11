package com.inturn.android.services

import android.media.session.MediaSession
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun getData(path: String, getSuccessFunction:(getData:DataSnapshot)->Unit, getErrorFunction:(error:DatabaseError)-> Unit){
    val database = Firebase.database
    val myRef = database.getReference(path)

    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            getSuccessFunction(dataSnapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            getErrorFunction(error)
        }
    })
}

fun postData(path : String, data : Any, postSuccessFunction:(getData:DataSnapshot)->Unit, postErrorFunction:(error:DatabaseError)-> Unit){
    val database = Firebase.database
    val myRef = database.getReference(path)

    val key = myRef.push().key.toString()
    myRef.child(key).setValue(data)

    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            postSuccessFunction(dataSnapshot.child(key))
        }

        override fun onCancelled(error: DatabaseError) {
            postErrorFunction(error)
        }
    })
}

fun updateData(path : String, data : Any, updateSuccessFunction:(getData:DataSnapshot)->Unit, updateErrorFunction:(error:DatabaseError)-> Unit){
    val database = Firebase.database
    val myRef = database.getReference(path)
    myRef.setValue(data)

    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            updateSuccessFunction(dataSnapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            updateErrorFunction(error)
        }
    })
}

fun dataChange(path : String ,changeSuccessFunction:(getData:DataSnapshot)->Unit, changeErrorFunction:(error:DatabaseError)-> Unit){
    val database = Firebase.database
    val myRef = database.getReference(path)

    myRef.addChildEventListener(object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//            changeSuccessFunction(snapshot)
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
//            changeSuccessFunction(snapshot)
        }

    })
}


