package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.AnotacaoDao
import com.example.data.local.dao.MarcacaoDao
import com.example.data.local.dao.ReferenciaCruzadaDao
import com.example.data.local.dao.VersiculoDao
import com.example.data.local.entity.Anotacao
import com.example.data.local.entity.Marcacao
import com.example.data.local.entity.ReferenciaCruzada
import com.example.data.local.entity.Versiculo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Versiculo::class,
        Anotacao::class,
        Marcacao::class,
        ReferenciaCruzada::class
    ],
    version = 2,
    exportSchema = false
)
abstract class BibliaRoomDatabase : RoomDatabase() {

    abstract fun versiculoDao(): VersiculoDao
    abstract fun anotacaoDao(): AnotacaoDao
    abstract fun marcacaoDao(): MarcacaoDao
    abstract fun referenciaCruzadaDao(): ReferenciaCruzadaDao

    companion object {
        @Volatile
        private var INSTANCE: BibliaRoomDatabase? = null

        fun getDatabase(context: Context): BibliaRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibliaRoomDatabase::class.java,
                    "biblia_sagrada_v4.db"
                )
                    .createFromAsset("databases/bibliaACF.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    INSTANCE?.let { database ->
                                        database.versiculoDao().deletePlaceholders()
                                        if (database.versiculoDao().countVersiculos("ACF") == 0) {
                                            database.versiculoDao().insertVersiculos(BibliaSeedData.getInitialVersiculos())
                                            database.referenciaCruzadaDao().insertReferencias(BibliaSeedData.getInitialReferenciasCruzadas())
                                        }
                                    }
                                } catch (_: Exception) {
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
