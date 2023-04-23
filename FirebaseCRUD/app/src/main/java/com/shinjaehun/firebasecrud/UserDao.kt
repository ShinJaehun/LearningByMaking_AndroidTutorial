package com.shinjaehun.firebasecrud

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class UserDao {
    private var databaseReference: DatabaseReference? = null

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("user")
    }

    fun add(user: User?): Task<Void> {
        return databaseReference!!.push().setValue(user)
    }

    fun getUserList(): Query? {
        return databaseReference
    }

    fun userUpdate(key: String, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReference!!.child(key).updateChildren(hashMap)
    }

    fun userDelete(key: String): Task<Void> {
        return databaseReference!!.child(key).removeValue()
    }
}