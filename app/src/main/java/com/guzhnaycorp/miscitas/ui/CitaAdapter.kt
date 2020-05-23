package com.guzhnaycorp.miscitas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guzhnaycorp.miscitas.R
import com.guzhnaycorp.miscitas.model.Cita
import kotlinx.android.synthetic.main.item_cita.view.*

class CitaAdapter
    : RecyclerView.Adapter<CitaAdapter.ViewHolder>() {

    var citas = ArrayList<Cita>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(cita: Cita) = with(itemView){
                tvCitaId.text = context.getString(R.string.item_cita_id,cita.id)
                tvDoctorName.text = cita.doctor
                tvScheduledDate.text = context.getString(R.string.item_cita_date,cita.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_cita_time,cita.scheduledTime)

                tvEspecialidad.text = cita.especialidad
                tvDescripcion.text = cita.descripcion
                tvStatus.text = cita.status
                tvType.text = cita.type
                tvCreatedAt.text = context.getString(R.string.item_cita_created_at,cita.createdAt)

                ibExpand.setOnClickListener{
                    if (linearLayoutDetails.visibility == View.VISIBLE){
                        linearLayoutDetails.visibility = View.GONE
                        ibExpand.setImageResource(R.drawable.ic_expand_more)
                    } else {
                        linearLayoutDetails.visibility = View.VISIBLE
                        ibExpand.setImageResource(R.drawable.ic_expand_less)
                    }
                }
            }
    }
    //inflates xml items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cita,
                parent,
                false
            )

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
