package com.guzhnaycorp.miscitas.model

data class HourInterval (val start: String, val end: String){
    override fun toString(): String {
        return "$start - $end"
    }
}