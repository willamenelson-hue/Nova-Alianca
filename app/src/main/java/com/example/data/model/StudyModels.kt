package com.example.data.model

data class BiblicalLocation(
    val id: String,
    val name: String,
    val modernName: String,
    val latitude: Double,
    val longitude: Double,
    val scriptureReferences: List<String>,
    val description: String,
    val historicalSignificance: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val biblicalReference: String,
    val explanation: String,
    val difficulty: String // "Iniciante", "Intermediário", "Avançado"
)
