package com.example.fittrack.di

import android.content.Context
import androidx.room.Room
import com.example.fittrack.Data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, Database::class.java, "Tracker")
            .createFromAsset("database/Tracker.db")
            //.fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideSerieCatalogoDao(db:Database) = db.EjercicioDao()

}
