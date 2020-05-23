package com.guzhnaycorp.miscitas.io.response

import com.guzhnaycorp.miscitas.model.User

data class LoginResponse (val success: Boolean, val user: User, val jwt: String)