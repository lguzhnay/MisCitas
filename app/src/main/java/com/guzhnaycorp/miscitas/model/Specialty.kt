package com.guzhnaycorp.miscitas.model

data class Specialty (val id: Int, val nombre: String){
    override fun toString(): String {
        return nombre
    }
}