package com.example.data.repository

import com.example.data.local.PreloadedData
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ChurchRepository {

    // Mensagens Pastorais
    private val _pastorMessages = MutableStateFlow(PreloadedData.pastorMessages)
    val pastorMessages: StateFlow<List<PastorMessage>> = _pastorMessages.asStateFlow()

    // Devocional Diário
    private val _dailyDevotional = MutableStateFlow(PreloadedData.currentDevotional)
    val dailyDevotional: StateFlow<DevotionalDaily> = _dailyDevotional.asStateFlow()

    // Mural de Oração em Tempo Real
    private val _prayerRequests = MutableStateFlow(PreloadedData.initialPrayerRequests)
    val prayerRequests: StateFlow<List<PrayerRequest>> = _prayerRequests.asStateFlow()

    // Agenda da Igreja
    private val _churchEvents = MutableStateFlow(PreloadedData.churchEvents)
    val churchEvents: StateFlow<List<ChurchEvent>> = _churchEvents.asStateFlow()

    // Hinários
    private val _harpaHymns = MutableStateFlow(PreloadedData.harpaCristaHymns)
    val harpaHymns: StateFlow<List<Hymn>> = _harpaHymns.asStateFlow()

    private val _cantorHymns = MutableStateFlow(PreloadedData.cantorCristaoHymns)
    val cantorHymns: StateFlow<List<Hymn>> = _cantorHymns.asStateFlow()

    private val _gospelSongs = MutableStateFlow(PreloadedData.gospelSongs)
    val gospelSongs: StateFlow<List<GospelSong>> = _gospelSongs.asStateFlow()

    // Recibos de Doação Pix do Usuário
    private val _donationReceipts = MutableStateFlow<List<GivingReceipt>>(emptyList())
    val donationReceipts: StateFlow<List<GivingReceipt>> = _donationReceipts.asStateFlow()

    // Locais Bíblicos e Quiz
    val biblicalLocations: List<BiblicalLocation> = PreloadedData.biblicalLocations
    val quizQuestions: List<QuizQuestion> = PreloadedData.quizQuestions

    // Ações de Oração
    fun addPrayerRequest(
        authorName: String,
        requestText: String,
        category: String,
        isPrivateToPastor: Boolean
    ) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
        val newRequest = PrayerRequest(
            id = UUID.randomUUID().toString(),
            authorName = if (authorName.isBlank()) "Membro Anônimo" else authorName,
            requestText = requestText,
            category = category,
            dateFormatted = dateFormat.format(Date()),
            prayCount = 1,
            isPrivateToPastor = isPrivateToPastor,
            hasUserPrayed = true
        )
        _prayerRequests.value = listOf(newRequest) + _prayerRequests.value
    }

    fun incrementPrayerCount(requestId: String) {
        val updated = _prayerRequests.value.map { req ->
            if (req.id == requestId) {
                if (req.hasUserPrayed) {
                    req.copy(prayCount = req.prayCount - 1, hasUserPrayed = false)
                } else {
                    req.copy(prayCount = req.prayCount + 1, hasUserPrayed = true)
                }
            } else {
                req
            }
        }
        _prayerRequests.value = updated
    }

    // Gerador de Código Pix "Copia e Cola" Padrão EMV BR Code
    fun generatePixPayload(type: GivingType, amount: Double): String {
        val key = PreloadedData.PIX_KEY
        val name = PreloadedData.PIX_NAME
        val city = "SAO PAULO"
        val desc = "IBNA ${type.label}".take(25)
        val formattedAmount = String.format(Locale.US, "%.2f", amount)

        // Payload Pix EMV padronizado para copiar e colar no app do banco
        return "00020126360014br.gov.bcb.pix0114${key}520400005303986540${formattedAmount}5802BR5925${name}6009${city}62170513${desc}6304"
    }

    // Registro de Doação
    fun recordDonation(type: GivingType, amount: Double, donorName: String): GivingReceipt {
        val receipt = GivingReceipt(
            id = UUID.randomUUID().toString().take(8).uppercase(),
            timestamp = System.currentTimeMillis(),
            type = type,
            amountFormatted = "R$ " + String.format(Locale("pt", "BR"), "%.2f", amount),
            donorName = if (donorName.isBlank()) "Membro da Igreja" else donorName,
            transactionRef = "PIX-IBNA-${System.currentTimeMillis().toString().takeLast(6)}"
        )
        _donationReceipts.value = listOf(receipt) + _donationReceipts.value
        return receipt
    }

    // Busca de Hinários por Número ou Texto
    fun searchHymns(collection: HymnCollection, query: String): List<Hymn> {
        val list = if (collection == HymnCollection.HARPA_CRISTA) _harpaHymns.value else _cantorHymns.value
        if (query.isBlank()) return list

        val numberQuery = query.toIntOrNull()
        return list.filter { hymn ->
            if (numberQuery != null && hymn.number == numberQuery) {
                true
            } else {
                hymn.title.contains(query, ignoreCase = true) ||
                        hymn.lyrics.contains(query, ignoreCase = true) ||
                        hymn.author.contains(query, ignoreCase = true)
            }
        }
    }

    // Busca de Músicas Gospel
    fun searchGospelSongs(query: String): List<GospelSong> {
        if (query.isBlank()) return _gospelSongs.value
        return _gospelSongs.value.filter { song ->
            song.title.contains(query, ignoreCase = true) ||
                    song.artist.contains(query, ignoreCase = true) ||
                    song.fullLyrics.contains(query, ignoreCase = true)
        }
    }
}
