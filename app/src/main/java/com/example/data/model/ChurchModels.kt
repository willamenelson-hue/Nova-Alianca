package com.example.data.model

data class PastorMessage(
    val id: String,
    val title: String,
    val pastorName: String = "Pr. Nelson Willame",
    val date: String,
    val summary: String,
    val fullText: String,
    val scriptureReference: String,
    val audioDurationMinutes: Int = 12,
    val tags: List<String> = listOf("Fé", "Família", "Edificação")
)

data class DevotionalDaily(
    val id: String,
    val date: String,
    val title: String,
    val keyVerseReference: String,
    val keyVerseText: String,
    val messageContent: String,
    val prayerOfFaith: String
)

data class PrayerRequest(
    val id: String,
    val authorName: String,
    val requestText: String,
    val category: String, // "Saúde", "Família", "Emprego", "Espiritual", "Agradecimento"
    val dateFormatted: String,
    val prayCount: Int = 0,
    val isPrivateToPastor: Boolean = false,
    val hasUserPrayed: Boolean = false
)

data class ChurchEvent(
    val id: String,
    val title: String,
    val dayOfWeek: String,
    val time: String,
    val location: String,
    val description: String,
    val ministry: String,
    val iconType: String = "church"
)

enum class GivingType(val label: String, val description: String) {
    DIZIMO("Dízimo", "Fidelidade e gratidão ao Senhor (Ml 3:10)"),
    OFERTA("Oferta de Amor", "Semeando na casa do Pai com alegria"),
    MISSOES("Missões Mundiais", "Apoio a missionários e evangelização"),
    CONSTRUCAO("Fundo de Construção", "Melhorias e expansão do templo")
}

data class GivingReceipt(
    val id: String,
    val timestamp: Long = System.currentTimeMillis(),
    val type: GivingType,
    val amountFormatted: String,
    val donorName: String,
    val transactionRef: String
)
