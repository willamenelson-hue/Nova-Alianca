package com.example.data.model

data class BibleBook(
    val id: Int,
    val name: String,
    val abbreviation: String,
    val testament: Testament,
    val totalChapters: Int,
    val category: String
)

enum class Testament {
    OLD, NEW
}

enum class BibleVersion(
    val code: String,
    val title: String,
    val subtitle: String,
    val year: String,
    val isOfflineDefault: Boolean
) {
    ACF("ACF", "Almeida Corrigida Fiel", "Texto Tradicional Fiel", "Domínio Público", true),
    JFAA("JFAA", "Almeida Atualizada", "João Ferreira de Almeida", "1877", true),
    JFAC("JFAC", "Almeida Corrigida", "Edição Histórica de 1848", "1848", true)
}

data class Verse(
    val bookId: Int,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val version: String = "ACF"
) {
    val reference: String get() = "$bookName $chapter:$verseNumber"
    val verseKey: String get() = "${version}_${bookId}_${chapter}_${verseNumber}"
    val bbcccvvvId: Int get() = (bookId * 1_000_000) + (chapter * 1_000) + verseNumber
}

data class ReferenciaComTexto(
    val origemVersiculoId: Int,
    val destinoVersiculoId: Int,
    val referenciaFormatada: String,
    val textoDestino: String,
    val contextoDescricao: String?
)

data class VerseHighlight(
    val verseKey: String,
    val colorHex: Long,
    val timestamp: Long = System.currentTimeMillis()
)

data class VerseNote(
    val id: Long = 0,
    val verseKey: String,
    val reference: String,
    val verseText: String,
    val note: String,
    val dateFormatted: String
)

data class ReadingTheme(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val passages: List<ThemePassage>
)

data class ThemePassage(
    val reference: String,
    val text: String,
    val devotionalReflection: String
)

data class ReadingPlan(
    val id: String,
    val title: String,
    val subtitle: String,
    val durationDays: Int,
    val category: String,
    val items: List<PlanDayItem>
)

data class PlanDayItem(
    val dayNumber: Int,
    val readingTarget: String,
    val chaptersCount: Int,
    val description: String,
    val isCompleted: Boolean = false
)
