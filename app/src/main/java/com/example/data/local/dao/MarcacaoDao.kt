package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.Marcacao
import kotlinx.coroutines.flow.Flow

@Dao
interface MarcacaoDao {

    @Query("SELECT * FROM marcacoes WHERE versiculoId = :versiculoId")
    fun getMarcacoesDoVersiculo(versiculoId: Int): Flow<List<Marcacao>>

    @Query(
        """
        SELECT * FROM marcacoes 
        WHERE versiculoId >= :minId AND versiculoId <= :maxId
        """
    )
    fun getMarcacoesDoCapitulo(minId: Int, maxId: Int): Flow<List<Marcacao>>

    @Query("SELECT * FROM marcacoes WHERE versiculoId IN (:versiculoIds)")
    fun getMarcacoesPorVersiculoIds(versiculoIds: List<Int>): Flow<List<Marcacao>>

    @Query("SELECT * FROM marcacoes ORDER BY dataCriacao DESC")
    fun getTodasMarcacoes(): Flow<List<Marcacao>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOuAtualizarMarcacao(marcacao: Marcacao): Long

    @Query("DELETE FROM marcacoes WHERE versiculoId = :versiculoId AND tipo = :tipo")
    suspend fun deleteMarcacaoPorVersiculoETipo(versiculoId: Int, tipo: String)

    @Query("DELETE FROM marcacoes WHERE versiculoId = :versiculoId")
    suspend fun deleteTodasMarcacoesDoVersiculo(versiculoId: Int)

    @Delete
    suspend fun deleteMarcacao(marcacao: Marcacao)
}
