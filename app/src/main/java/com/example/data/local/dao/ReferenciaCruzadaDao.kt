package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.ReferenciaCruzada
import kotlinx.coroutines.flow.Flow

@Dao
interface ReferenciaCruzadaDao {

    @Query(
        """
        SELECT * FROM referencias_cruzadas 
        WHERE origemVersiculoId = :versiculoId 
        ORDER BY destinoVersiculoId ASC
        """
    )
    fun getReferenciasCruzadas(versiculoId: Int): Flow<List<ReferenciaCruzada>>

    @Query(
        """
        SELECT DISTINCT origemVersiculoId FROM referencias_cruzadas 
        WHERE origemVersiculoId >= :minId AND origemVersiculoId <= :maxId
        """
    )
    fun getOrigemIdsComReferenciasNoCapitulo(minId: Int, maxId: Int): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReferencias(referencias: List<ReferenciaCruzada>)

    @Query("SELECT COUNT(*) FROM referencias_cruzadas")
    suspend fun countReferencias(): Int
}
