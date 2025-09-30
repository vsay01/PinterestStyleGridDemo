package com.vsay.pintereststylegriddemo.core.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.vsay.pintereststylegriddemo.core.domain.model.User

/**
 * An extension function to convert a com.google.firebase.auth.FirebaseUser object into your domain's com.yourpackage.core.domain.model.User object.
 * This keeps your domain model clean of Firebase types and centralizes the mapping logic.
 */
fun FirebaseUser.toDomainUser(): User {
    return User(
        uid = this.uid,
        email = this.email,
        displayName = this.displayName
    )
}
