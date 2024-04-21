package com.example.fittrack.login

data class FiTrackUser(
    val username: String,
    val email: String,
    val hashedpassword: String,
    val foto: String
)

data class UserData(
    val username: String,
    val hashed_password: String
)