package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BbcccvvvHelper
import com.example.data.local.PreloadedData
import com.example.data.local.entity.Anotacao
import com.example.data.local.entity.Marcacao
import com.example.data.local.entity.Versiculo
import com.example.data.model.*
import com.example.data.repository.BibleRepository
import com.example.data.repository.ChurchRepository
import com.example.service.TtsReaderService
import com.example.service.TtsState
import com.example.service.VoiceGender
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppDestination {
    HOME,
    BIBLE,
    HYMNS,
    PRAYER,
    MORE,
    GIVING,
    PLANS,
    AGENDA,
    STUDY_MAPS,
    QUIZ,
    MY_NOTES
}

data class BibleReaderUiState(
    val currentBookId: Int = 19, // Salmos
    val currentChapter: Int = 23,
    val selectedVersion: BibleVersion = BibleVersion.ACF,
    val fontSizeSp: Int = 18,
    val readerTheme: ReaderColorMode = ReaderColorMode.DEFAULT,
    val searchQuery: String = "",
    val searchResults: List<Verse> = emptyList(),
    val isSearchOpen: Boolean = false,
    val isBookSelectorOpen: Boolean = false
)

enum class ReaderColorMode(val label: String) {
    DEFAULT("Padrão"),
    PARCHMENT("Pergaminho"),
    DARK("Escuro")
}

data class QuizUiState(
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val score: Int = 0,
    val isFinished: Boolean = false
)

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val bibleRepository = BibleRepository(application)
    val churchRepository = ChurchRepository()
    val ttsService = TtsReaderService(application)

    // Navegação
    private val _currentDestination = MutableStateFlow(AppDestination.HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    // Estado do Leitor Bíblico
    private val _bibleState = MutableStateFlow(BibleReaderUiState())
    val bibleState: StateFlow<BibleReaderUiState> = _bibleState.asStateFlow()

    // Versículos reativos do SQLite Room para o capítulo atual
    private val _versiculosDoCapitulo = MutableStateFlow<List<Versiculo>>(emptyList())
    val versiculosDoCapitulo: StateFlow<List<Versiculo>> = _versiculosDoCapitulo.asStateFlow()

    // Marcações do capítulo atual (chave = versiculoId BBCCCVVV)
    private val _marcacoesDoCapitulo = MutableStateFlow<Map<Int, List<Marcacao>>>(emptyMap())
    val marcacoesDoCapitulo: StateFlow<Map<Int, List<Marcacao>>> = _marcacoesDoCapitulo.asStateFlow()

    // IDs de versículos com referências cruzadas no capítulo
    private val _referenciasIdsNoCapitulo = MutableStateFlow<Set<Int>>(emptySet())
    val referenciasIdsNoCapitulo: StateFlow<Set<Int>> = _referenciasIdsNoCapitulo.asStateFlow()

    // Estado do Quiz
    private val _quizState = MutableStateFlow(QuizUiState())
    val quizState: StateFlow<QuizUiState> = _quizState.asStateFlow()

    // Mensagens de Toast / Feedback ao usuário
    private val _userFeedbackMessage = MutableStateFlow<String?>(null)
    val userFeedbackMessage: StateFlow<String?> = _userFeedbackMessage.asStateFlow()

    // Versículo do Dia Atual
    val dailyVerse: Verse get() {
        val index = (System.currentTimeMillis() / (1000 * 60 * 60 * 24) % PreloadedData.dailyVerses.size).toInt()
        return PreloadedData.dailyVerses[index]
    }

    init {
        carregarCapituloAtual()
    }

    fun navigateTo(destination: AppDestination) {
        _currentDestination.value = destination
    }

    fun showFeedback(message: String) {
        _userFeedbackMessage.value = message
    }

    fun clearFeedback() {
        _userFeedbackMessage.value = null
    }

    // --- Ações Bíblicas ---
    fun selectBookAndChapter(bookId: Int, chapter: Int) {
        _bibleState.value = _bibleState.value.copy(
            currentBookId = bookId,
            currentChapter = chapter,
            isBookSelectorOpen = false
        )
        carregarCapituloAtual()
    }

    fun nextChapter() {
        val currentBook = bibleRepository.getBookById(_bibleState.value.currentBookId) ?: return
        if (_bibleState.value.currentChapter < currentBook.totalChapters) {
            selectBookAndChapter(_bibleState.value.currentBookId, _bibleState.value.currentChapter + 1)
        } else {
            val nextBook = bibleRepository.getBookById(_bibleState.value.currentBookId + 1)
            if (nextBook != null) {
                selectBookAndChapter(nextBook.id, 1)
            }
        }
    }

    fun previousChapter() {
        if (_bibleState.value.currentChapter > 1) {
            selectBookAndChapter(_bibleState.value.currentBookId, _bibleState.value.currentChapter - 1)
        } else if (_bibleState.value.currentBookId > 1) {
            val prevBook = bibleRepository.getBookById(_bibleState.value.currentBookId - 1)
            if (prevBook != null) {
                selectBookAndChapter(prevBook.id, prevBook.totalChapters)
            }
        }
    }

    fun changeVersion(version: BibleVersion) {
        bibleRepository.setBibleVersion(version)
        _bibleState.value = _bibleState.value.copy(selectedVersion = version)
        carregarCapituloAtual()
        showFeedback("Versão ativa: ${version.title} (${version.year})")
    }

    fun toggleBookSelector(isOpen: Boolean) {
        _bibleState.value = _bibleState.value.copy(isBookSelectorOpen = isOpen)
    }

    fun setFontSize(sizeSp: Int) {
        _bibleState.value = _bibleState.value.copy(fontSizeSp = sizeSp.coerceIn(14, 28))
    }

    fun setReaderTheme(theme: ReaderColorMode) {
        _bibleState.value = _bibleState.value.copy(readerTheme = theme)
    }

    fun performBibleSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) {
            _bibleState.value = _bibleState.value.copy(
                searchQuery = "",
                searchResults = emptyList(),
                isSearchOpen = false
            )
            return
        }
        _bibleState.value = _bibleState.value.copy(searchQuery = query, isSearchOpen = true)
        viewModelScope.launch {
            bibleRepository.buscarVersiculos(trimmed).collectLatest { versiculos ->
                val results = versiculos.map { v ->
                    Verse(
                        bookId = v.livro,
                        bookName = BbcccvvvHelper.getBookName(v.livro),
                        chapter = v.capitulo,
                        verseNumber = v.numero,
                        text = v.texto,
                        version = v.versao
                    )
                }
                _bibleState.value = _bibleState.value.copy(searchResults = results)
            }
        }
    }

    fun closeSearch() {
        _bibleState.value = _bibleState.value.copy(isSearchOpen = false, searchQuery = "")
    }

    private var carregarCapituloJob: kotlinx.coroutines.Job? = null

    private fun carregarCapituloAtual() {
        carregarCapituloJob?.cancel()
        val bookId = _bibleState.value.currentBookId
        val chapter = _bibleState.value.currentChapter
        val minId = BbcccvvvHelper.getChapterMinId(bookId, chapter)
        val maxId = BbcccvvvHelper.getChapterMaxId(bookId, chapter)
        val version = _bibleState.value.selectedVersion.code

        carregarCapituloJob = viewModelScope.launch {
            launch {
                bibleRepository.getVersiculosPorCapituloFlow(bookId, chapter, version)
                    .collect { list ->
                        _versiculosDoCapitulo.value = list
                    }
            }
            launch {
                bibleRepository.getMarcacoesDoCapitulo(minId, maxId)
                    .collect { marcacoes ->
                        _marcacoesDoCapitulo.value = marcacoes.groupBy { it.versiculoId }
                    }
            }
            launch {
                bibleRepository.getOrigemIdsComReferenciasNoCapitulo(minId, maxId)
                    .collect { ids ->
                        _referenciasIdsNoCapitulo.value = ids.toSet()
                    }
            }
        }
    }

    // --- Marcações Room (Marca-Texto & Sublinhado) ---
    fun destacarCor(versiculoId: Int, corHex: String) {
        viewModelScope.launch {
            bibleRepository.salvarMarcacao(versiculoId, "marcatexto", corHex)
            showFeedback("Marca-texto aplicado com sucesso!")
        }
    }

    fun sublinharVersiculo(versiculoId: Int, corHex: String = "#D4AF37") {
        viewModelScope.launch {
            bibleRepository.salvarMarcacao(versiculoId, "sublinhado", corHex)
            showFeedback("Versículo sublinhado com sucesso!")
        }
    }

    fun removerMarcacao(versiculoId: Int, tipo: String) {
        viewModelScope.launch {
            bibleRepository.removerMarcacao(versiculoId, tipo)
            showFeedback("Marcação removida")
        }
    }

    fun limparTodasMarcacoes(versiculoId: Int) {
        viewModelScope.launch {
            bibleRepository.limparTodasMarcacoesDoVersiculo(versiculoId)
            showFeedback("Marcações removidas do versículo")
        }
    }

    // --- Anotações Room ---
    fun salvarAnotacao(versiculoId: Int, texto: String) {
        viewModelScope.launch {
            if (texto.isNotBlank()) {
                bibleRepository.salvarAnotacao(versiculoId, texto)
                showFeedback("Anotação salva com sucesso!")
            }
        }
    }

    fun getAnotacoesDoVersiculo(versiculoId: Int): Flow<List<Anotacao>> {
        return bibleRepository.getAnotacoesDoVersiculo(versiculoId)
    }

    // --- Referências Cruzadas TSK ---
    fun getReferenciasCruzadasComTexto(versiculoId: Int): Flow<List<ReferenciaComTexto>> {
        return bibleRepository.getReferenciasCruzadasComTexto(versiculoId)
    }

    // --- TTS (Áudio da Bíblia) ---
    val ttsState: StateFlow<TtsState> = ttsService.ttsState

    fun startReadingCurrentChapter() {
        val versiculos = _versiculosDoCapitulo.value
        val book = bibleRepository.getBookById(_bibleState.value.currentBookId)
        val verses = versiculos.map {
            Verse(
                bookId = it.livro,
                bookName = book?.name ?: "",
                chapter = it.capitulo,
                verseNumber = it.numero,
                text = it.texto,
                version = it.versao
            )
        }
        if (verses.isNotEmpty()) {
            ttsService.readVerses(verses)
        }
    }

    fun readSpecificVerse(versiculo: Versiculo) {
        val book = bibleRepository.getBookById(versiculo.livro)
        val v = Verse(
            bookId = versiculo.livro,
            bookName = book?.name ?: "",
            chapter = versiculo.capitulo,
            verseNumber = versiculo.numero,
            text = versiculo.texto,
            version = versiculo.versao
        )
        ttsService.readVerses(listOf(v))
    }

    fun togglePlayPauseTts() {
        if (ttsState.value.isSpeaking) {
            ttsService.pause()
        } else {
            if (ttsState.value.currentVerseIndex != -1) {
                ttsService.resume()
            } else {
                startReadingCurrentChapter()
            }
        }
    }

    fun stopTts() {
        ttsService.stop()
    }

    fun setVoiceGender(gender: VoiceGender) {
        ttsService.setGender(gender)
        showFeedback("Voz do leitor alterada para ${gender.label}")
    }

    fun setSpeechRate(rate: Float) {
        ttsService.setSpeechRate(rate)
    }

    // --- Quiz Bíblico ---
    fun answerQuizQuestion(optionIndex: Int) {
        val currentQ = churchRepository.quizQuestions.getOrNull(_quizState.value.currentQuestionIndex) ?: return
        val isCorrect = optionIndex == currentQ.correctIndex
        val newScore = if (isCorrect) _quizState.value.score + 10 else _quizState.value.score
        _quizState.value = _quizState.value.copy(
            selectedOptionIndex = optionIndex,
            isAnswerSubmitted = true,
            score = newScore
        )
    }

    fun nextQuizQuestion() {
        val nextIdx = _quizState.value.currentQuestionIndex + 1
        if (nextIdx < churchRepository.quizQuestions.size) {
            _quizState.value = _quizState.value.copy(
                currentQuestionIndex = nextIdx,
                selectedOptionIndex = null,
                isAnswerSubmitted = false
            )
        } else {
            _quizState.value = _quizState.value.copy(isFinished = true)
        }
    }

    fun resetQuiz() {
        _quizState.value = QuizUiState()
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.shutdown()
    }
}
