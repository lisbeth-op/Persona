package com.example.persona.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.persona.data.local.dao.PersonaDao
import com.example.persona.data.local.entity.PersonaEntity

@Database(
    entities = [PersonaEntity::class ],
    version = 1,
    exportSchema = false
)
abstract class PersonaDb: RoomDatabase(){
    abstract fun PersonaDao(): PersonaDao
}