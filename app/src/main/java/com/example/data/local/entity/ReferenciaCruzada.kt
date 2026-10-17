package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * Entidade Room para o sistema de referências cruzadas bíblicas
 * baseado no Treasury of Scripture Knowledge (TSK - Domínio Público).
 *
 * [origemVersiculoId]: BBCCCVVV do versículo de origem
 * [destinoVersiculoId]: BBCCCVVV do versículo interligado
 * [contextoDescricao]: descrição ou tema da correlação teológica
 */
@Entity(
    tableName = "referencias_cruzadas",
    primaryKeys = ["origemVersiculoId", "destinoVersiculoId"],
    indices = [
        Index(value = ["origemVersiculoId"]),
        Index(value = ["destinoVersiculoId"])
    ]
)
data class ReferenciaCruzada(
    val origemVersiculoId: Int, // BBCCCVVV
    val destinoVersiculoId: Int, // BBCCCVVV
    val contextoDescricao: String? = null
)
