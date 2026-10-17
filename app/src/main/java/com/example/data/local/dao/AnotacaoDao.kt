package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.Anotacao
import kotlinx.coroutines.flow.Flow

@Dao
interface AnotacaoDao {

    @Query("SELECT * FROM anotacoes WHERE versiculoId = :versiculoId ORDER BY dataCriacao DESC")
    fun getAnotacoesDoVersiculo(versiculoId: Int): Flow<List<Anotacao>>

    @Query("SELECT * FROM anotacoes WHERE versiculoId IN (:versiculoIds) ORDER BY dataCriacao DESC")
    fun getAnotacoesPorVersiculoIds(versiculoIds: List<Int>): Flow<List<Anotacao>>

    @Query("SELECT * FROM anotacoes ORDER BY dataCriacao DESC")
    fun getTodasAnotacoes(): Flow<List<Anotacao>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnotacao(anotacao: Anotacao): Long

    @Update
    suspend fun updateAnotacao(anotacao: Anotacao)

    @Delete
    suspend fun deleteAnotacao(anotacao: Anotacao)

    @Query("DELETE FROM anotacoes WHERE id = :id")
    suspend fun deleteAnotacaoPorId(id: Long)
}
