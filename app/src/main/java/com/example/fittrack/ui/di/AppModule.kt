package com.example.fittrack.ui.di

import android.content.Context
import androidx.room.Room
import com.example.fittrack.ui.Data.InventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, InventoryDatabase::class.java, "tracker")
            .createFromAsset("database/tracker.db")
            .build()

    @Singleton
    @Provides
    fun provideEjercicioDao(db: InventoryDatabase) = db.EjercicioDao()

}
