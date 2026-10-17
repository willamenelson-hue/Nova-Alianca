package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade Room para anotações pessoais do fiel em um versículo.
 * [versiculoId] armazena o código BBCCCVVV do versículo comentado.
 */
@Entity(
    tableName = "anotacoes",
    indices = [Index(value = ["versiculoId"])]
)
data class Anotacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val versiculoId: Int, // BBCCCVVV
    val textoAnotacao: String,
    val dataCriacao: Long = System.currentTimeMillis()
)
