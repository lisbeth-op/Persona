package com.example.persona.di

import android.content.Context
import androidx.room.Room
import com.example.persona.data.PersonaDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesTicketDatabase(@ApplicationContext appContext: Context): PersonaDb =
        Room.databaseBuilder(
            appContext,
            PersonaDb::class.java,
            "PersonaDb.db")
            .fallbackToDestructiveMigration()
            .build()
}