package com.example.persona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Personas")
class PersonaEntity (
    @PrimaryKey
    val PersonaId: Int?=null,
    var Nombre: String = "",
    var Telefono: String = "",
    var Celular: String = "",
    var Email: String = "",
    var FechaNacimiento: String = "",
    var Ocupacion: String = ""

)