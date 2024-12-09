package com.example.firebaseiot.Models

import android.os.Parcel
import android.os.Parcelable

// Definición de la clase Contacto que implementa Parcelable
data class Contacto(
    val id: String? = null, // ID del contacto
    val nombre: String? = null, // Nombre del contacto
    val correo: String? = null, // Correo del contacto
    val numero: String? = null // Número del contacto
) : Parcelable {
    // Constructor secundario que recibe un Parcel y extrae los datos
    constructor(parcel: Parcel) : this(
        parcel.readString(), // Leer el ID del Parcel
        parcel.readString(), // Leer el nombre del Parcel
        parcel.readString(), // Leer el correo del Parcel
        parcel.readString() // Leer el número del Parcel
    )

    //metodo para escribir los datos del contacto en un parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id) // Escribir el ID en el Parcel
        parcel.writeString(nombre) // Escribir el nombre en el Parcel
        parcel.writeString(correo) // Escribir el correo en el Parcel
        parcel.writeString(numero) // Escribir el número en el Parcel
    }

    //metodo que describe el contenido del parcelable (no se usa habitualmente)
    override fun describeContents(): Int {
        return 0
    }

    // Objeto CREATOR necesario para crear instancias de la clase Contacto desde un Parcel
    companion object CREATOR : Parcelable.Creator<Contacto> {
        //metodo para crear la instancia de contacto desde el parcel
        override fun createFromParcel(parcel: Parcel): Contacto {
            return Contacto(parcel)
        }

        //metodo para crear un array de contacto (habitualemnte tampooc se usa)
        override fun newArray(size: Int): Array<Contacto?> {
            return arrayOfNulls(size)
        }
    }
}
