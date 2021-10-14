package com.example.academyapp.di

import android.content.Context
import androidx.room.Room
import com.example.academyapp.domain.local.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object databaseModule {

    @Provides
    @Singleton
    fun providePrefs(@ApplicationContext context: Context) =
        context.getSharedPreferences("academyApp", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        DataBase::class.java,
        "AlinaDataBase"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(db: DataBase) = db.getUserDao()

}