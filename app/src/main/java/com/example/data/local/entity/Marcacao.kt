package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade Room para marcações de texto bíblico.
 * [tipo] suporta "marcatexto" ou "sublinhado".
 * [corHex] código hexadecimal da cor (ex: "#FFF176", "#A5D6A7", "#90CAF9", "#F48FB1").
 */
@Entity(
    tableName = "marcacoes",
    indices = [
        Index(value = ["versiculoId"]),
        Index(value = ["versiculoId", "tipo"], unique = true)
    ]
)
data class Marcacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val versiculoId: Int, // BBCCCVVV
    val tipo: String, // "marcatexto" ou "sublinhado"
    val corHex: String,
    val dataCriacao: Long = System.currentTimeMillis()
)
