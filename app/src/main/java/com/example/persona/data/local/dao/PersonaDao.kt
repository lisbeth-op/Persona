package com.example.persona.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.persona.data.local.entity.PersonaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(Persona: PersonaEntity)

    @Query(
        """
        SELECT * 
        FROM Personas
        WHERE PersonaId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): PersonaEntity?

    @Delete
    suspend fun delete(ticket: PersonaEntity)

    @Query("SELECT * FROM Personas")
    fun getAll(): Flow<List<PersonaEntity>>



}