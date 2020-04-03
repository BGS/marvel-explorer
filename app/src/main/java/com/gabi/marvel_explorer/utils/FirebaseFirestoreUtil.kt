package com.gabi.marvel_explorer.utils


import android.graphics.Bitmap
import com.gabi.marvel_explorer.model.FavoriteComics
import com.gabi.marvel_explorer.model.FirebaseUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

import java.io.ByteArrayOutputStream


object FirebaseFirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID is null.")}")



    fun initProfile(name: String = "", email: String = "", profilePictureBitmap: Bitmap? = null,
                    finished: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {

                if (profilePictureBitmap != null) {
                    val stream = ByteArrayOutputStream()
                    profilePictureBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val imageByteArray = stream.toByteArray()

                    FirebaseStorageUtil.uploadProfilePhoto(imageByteArray) {
                        val newUser = FirebaseUser(FirebaseAuth.getInstance().currentUser?.uid ?: "", name,
                                it, email, emptyList())

                        currentUserDocRef.set(newUser).addOnSuccessListener { finished() }
                    }
                }
            }
        }
    }

    fun updateCurrentUser(name: String = "", email: String = "", profilePicturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (profilePicturePath != null)
            userFieldMap["profilePicturePath"] = profilePicturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun pushFavoriteComicCurrentUser(favoriteComic: FavoriteComics) {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            firestoreInstance.document("users/${it}").update("favoriteComics",
                    FieldValue.arrayUnion(favoriteComic))
        }

    }

    fun getCurrentUserProfileRef(): Task<DocumentSnapshot> {
        return  firestoreInstance.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID is null.")).get()


    }

}