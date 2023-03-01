package com.example.calculadoraimc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImcEntity::class],version = 1)
abstract class ImcDataBase: RoomDatabase() {

    abstract fun ImcDao():ImcDao


    companion object {

        @Volatile
        private var INSTANCE: ImcDataBase? = null

        fun getInstance(context: Context): ImcDataBase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ImcDataBase::class.java,
                        "Imc_database"
                    )

                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}