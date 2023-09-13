package com.example.persona.data.Repository


import com.example.persona.data.PersonaDb
import com.example.persona.data.local.entity.PersonaEntity
import javax.inject.Inject

class PersonaRepository @Inject constructor(
    private val PersonasDb: PersonaDb
)
{
    suspend fun  save(persona: PersonaEntity) = PersonasDb.PersonaDao().save(persona)
    fun getAll() = PersonasDb.PersonaDao().getAll()
    suspend fun delete(persona: PersonaEntity)= PersonasDb.PersonaDao().delete(persona)
}