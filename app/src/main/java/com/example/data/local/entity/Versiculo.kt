package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * Entidade Room representando um versículo bíblico.
 * [id] segue o padrão universal BBCCCVVV:
 * Ex: João 3:16 -> 43003016
 *
 * Chave primária composta por (id, versao) permitindo coexistência das versões:
 * ACF (Almeida Corrigida Fiel), JFAA (Almeida Atualizada 1877), JFAC (Almeida Corrigida 1848).
 */
@Entity(
    tableName = "versiculos",
    primaryKeys = ["id", "versao"],
    indices = [
        Index(value = ["versao", "livro", "capitulo"]),
        Index(value = ["id"])
    ]
)
data class Versiculo(
    val id: Int, // BBCCCVVV
    val versao: String, // "ACF", "JFAA", "JFAC"
    val livro: Int, // 1..66
    val capitulo: Int,
    val numero: Int,
    val texto: String
)
