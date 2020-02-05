package com.guzhnaycorp.miscitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guzhnaycorp.miscitas.model.Cita
import kotlinx.android.synthetic.main.activity_citas.*

class CitasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        val citas = ArrayList<Cita>()
        citas.add(
            Cita(1,"Médico Test", "12/02/2020","3:00 PM")
        )
        citas.add(
            Cita(2,"Médico ABC", "15/02/2020","4:00 PM")
        )
        citas.add(
            Cita(3,"Médico DEF", "17/02/2020","7:00 AM")
        )

        rvCitas.layoutManager = LinearLayoutManager(this)
        rvCitas.adapter = CitaAdapter(citas)
    }
}
