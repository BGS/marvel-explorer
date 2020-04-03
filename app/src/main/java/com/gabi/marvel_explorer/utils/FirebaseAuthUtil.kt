package com.gabi.marvel_explorer.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference

object FirebaseAuthUtil {

    private val authInstance: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun authenticateUser(email: String, password: String, authSuccess: (Boolean) ->  Unit) {
        authInstance.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                authSuccess(true)
            } else {
                authSuccess(false)
            }
        }
    }



    val currentUser: FirebaseUser?
        get() = authInstance.currentUser
}