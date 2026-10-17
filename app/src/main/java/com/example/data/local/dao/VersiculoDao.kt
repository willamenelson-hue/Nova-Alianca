package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.Versiculo
import kotlinx.coroutines.flow.Flow

@Dao
interface VersiculoDao {

    @Query(
        """
        SELECT * FROM versiculos 
        WHERE versao = :versao AND livro = :livro AND capitulo = :capitulo 
        ORDER BY numero ASC
        """
    )
    fun getVersiculosPorCapitulo(versao: String, livro: Int, capitulo: Int): Flow<List<Versiculo>>

    @Query("SELECT * FROM versiculos WHERE versao = :versao AND id = :id LIMIT 1")
    suspend fun getVersiculoPorId(versao: String, id: Int): Versiculo?

    @Query("SELECT * FROM versiculos WHERE versao = :versao AND id IN (:ids) ORDER BY id ASC")
    suspend fun getVersiculosPorIds(versao: String, ids: List<Int>): List<Versiculo>

    @Query(
        """
        SELECT * FROM versiculos 
        WHERE versao = :versao AND texto LIKE '%' || :query || '%'
        ORDER BY id ASC LIMIT 100
        """
    )
    fun buscarVersiculos(versao: String, query: String): Flow<List<Versiculo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVersiculos(versiculos: List<Versiculo>)

    @Query("SELECT COUNT(*) FROM versiculos WHERE versao = :versao")
    suspend fun countVersiculos(versao: String): Int

    @Query("DELETE FROM versiculos WHERE texto LIKE 'E sucedeu no livro de%' OR texto LIKE 'E aconteceo no livro de%'")
    suspend fun deletePlaceholders()
}
