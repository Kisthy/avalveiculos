package com.github.kisthy.avalveiculos.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.kisthy.avalveiculos.model.Avaliacao
import com.github.kisthy.avalveiculos.util.DateConverters

@Database(entities = [Avaliacao::class], version = 2)
@TypeConverters(DateConverters::class)
abstract class AvaliacaoDb : RoomDatabase(){

    abstract fun avaliacaoDao(): AvaliacaoDao

    companion object {

        private lateinit var instance: AvaliacaoDb

        fun getDatabase(context: Context): AvaliacaoDb {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        AvaliacaoDb::class.java,
                        "contato_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

}