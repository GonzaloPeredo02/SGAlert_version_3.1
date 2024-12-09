package com.example.firebaseiot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseiot.Models.Contacto
import com.example.firebaseiot.R

class AdapterContacto(private var contactos: ArrayList<Contacto>, private val onEditClick: (Contacto) -> Unit, private val onDeleteClick: (Contacto) -> Unit) :
    RecyclerView.Adapter<AdapterContacto.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val correo: TextView = itemView.findViewById(R.id.tvCorreo)
        val numero: TextView = itemView.findViewById(R.id.tvNumero)
        //se agregan manejo de botones de editar y eliminar
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contacto = contactos[position]
        holder.nombre.text = contacto.nombre
        holder.correo.text = contacto.correo
        holder.numero.text = contacto.numero

        holder.btnEditar.setOnClickListener {
            onEditClick(contacto)
        }

        holder.btnEliminar.setOnClickListener {
            onDeleteClick(contacto)
        }
    }

    override fun getItemCount(): Int {
        return contactos.size
    }
}
