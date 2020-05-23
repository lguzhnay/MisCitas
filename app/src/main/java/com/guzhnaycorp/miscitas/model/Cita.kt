package com.guzhnaycorp.miscitas.model

import com.google.gson.annotations.SerializedName

data class Cita (
    val id:Int,
    val descripcion: String,
    val type: String,
    val status: String,
    val doctor:String,
    @SerializedName("scheduled_date") val scheduledDate: String,
    @SerializedName("scheduled_time") val scheduledTime: String,
    @SerializedName("created_at") val createdAt: String,

    val especialidad: String
)
