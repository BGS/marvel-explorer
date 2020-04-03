package com.gabi.marvel_explorer.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

object FirebaseStorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    fun uploadProfilePhoto(imageBytes: ByteArray,
                           profilePic: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("/images/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    profilePic(ref.path)
                }.addOnFailureListener {
                    profilePic("")
                }
    }

    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().currentUser?.uid
                        ?: throw NullPointerException("UID is null."))

    fun pathToReference(path: String) = storageInstance.getReference(path)
}