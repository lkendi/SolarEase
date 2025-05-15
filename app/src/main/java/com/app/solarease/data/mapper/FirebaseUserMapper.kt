package com.app.solarease.data.mapper

import com.app.solarease.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toDomainUser() = this?.let {
    User(
        id = uid,
        displayName = displayName,
        email = email,
        photoUrl = photoUrl?.toString(),
    )
}
