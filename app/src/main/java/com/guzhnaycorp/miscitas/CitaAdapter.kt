package com.guzhnaycorp.miscitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guzhnaycorp.miscitas.model.Cita
import kotlinx.android.synthetic.main.item_cita.view.*

class CitaAdapter(private val citas: ArrayList<Cita>)
    : RecyclerView.Adapter<CitaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(cita: Cita) = with(itemView){
                tvCitaId.text = context.getString(R.string.item_cita_id,cita.id)
                tvDoctorName.text = cita.doctorName
                tvScheduledDate.text = context.getString(R.string.item_cita_date,cita.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_cita_time,cita.scheduledTime)
            }
    }
    //inflates xml items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cita,parent,false)

        )
    }

    //Enlazar la data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citas[position]

        holder.bind(cita)
    }

    //Obtiene el numero de elementos
    override fun getItemCount() = citas.size

}
