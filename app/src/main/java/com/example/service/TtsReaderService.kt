package com.example.service

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.example.data.model.Verse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

enum class VoiceGender(val label: String) {
    FEMALE("Feminina"),
    MALE("Masculina")
}

data class TtsState(
    val isSpeaking: Boolean = false,
    val currentVerseIndex: Int = -1,
    val currentVerseNumber: Int = -1,
    val selectedGender: VoiceGender = VoiceGender.FEMALE,
    val speechRate: Float = 1.0f,
    val isInitialized: Boolean = false,
    val availableVoicesCount: Int = 0
)

class TtsReaderService(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val _ttsState = MutableStateFlow(TtsState())
    val ttsState: StateFlow<TtsState> = _ttsState.asStateFlow()

    private var versesQueue: List<Verse> = emptyList()
    private var currentIndex: Int = -1
    private var isPaused: Boolean = false

    init {
        // Inicializa priorizando a Google TTS Engine oficial quando disponível
        tts = TextToSpeech(context, this, "com.google.android.tts")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("pt", "BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.language = Locale.getDefault()
            }
            setupVoiceSelection(_ttsState.value.selectedGender)
            setupProgressListener()
            val voicesCount = tts?.voices?.size ?: 0
            _ttsState.value = _ttsState.value.copy(
                isInitialized = true,
                availableVoicesCount = voicesCount
            )
        } else {
            // Fallback para engine padrão do sistema se Google TTS falhar
            tts = TextToSpeech(context) { fallbackStatus ->
                if (fallbackStatus == TextToSpeech.SUCCESS) {
                    tts?.language = Locale("pt", "BR")
                    setupVoiceSelection(_ttsState.value.selectedGender)
                    setupProgressListener()
                    _ttsState.value = _ttsState.value.copy(isInitialized = true)
                }
            }
        }
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _ttsState.value = _ttsState.value.copy(isSpeaking = true)
            }

            override fun onDone(utteranceId: String?) {
                if (utteranceId?.startsWith("verse_") == true) {
                    playNextVerse()
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _ttsState.value = _ttsState.value.copy(isSpeaking = false)
            }
        })
    }

    fun setGender(gender: VoiceGender) {
        _ttsState.value = _ttsState.value.copy(selectedGender = gender)
        setupVoiceSelection(gender)
    }

    fun setSpeechRate(rate: Float) {
        _ttsState.value = _ttsState.value.copy(speechRate = rate)
        tts?.setSpeechRate(rate)
    }

    private fun setupVoiceSelection(gender: VoiceGender) {
        val currentTts = tts ?: return
        try {
            val allVoices = currentTts.voices ?: emptySet()
            val targetLocale = Locale("pt", "BR")

            // Procura voz no Google TTS correspondente ao gênero e idioma
            val matchingVoice = allVoices.firstOrNull { voice ->
                val matchesLang = voice.locale.language == targetLocale.language
                val isMale = voice.name.contains("male", ignoreCase = true) ||
                        voice.name.contains("masc", ignoreCase = true) ||
                        voice.name.contains("ptd", ignoreCase = true)
                val isFemale = voice.name.contains("female", ignoreCase = true) ||
                        voice.name.contains("fem", ignoreCase = true) ||
                        voice.name.contains("afs", ignoreCase = true)

                if (gender == VoiceGender.MALE) {
                    matchesLang && (isMale || (!isFemale && voice.name.contains("male")))
                } else {
                    matchesLang && (isFemale || (!isMale && voice.name.contains("female")))
                }
            } ?: allVoices.firstOrNull { it.locale.language == targetLocale.language }

            if (matchingVoice != null) {
                currentTts.voice = matchingVoice
            }

            // Ajuste complementar de tom (pitch) para reforçar voz masculina/feminina distintiva
            if (gender == VoiceGender.MALE) {
                currentTts.setPitch(0.85f)
            } else {
                currentTts.setPitch(1.08f)
            }
        } catch (_: Exception) {
            // Fallback elegante se gerenciador de vozes falhar
            if (gender == VoiceGender.MALE) currentTts.setPitch(0.82f) else currentTts.setPitch(1.1f)
        }
    }

    fun readVerses(verses: List<Verse>, startIndex: Int = 0) {
        if (verses.isEmpty()) return
        stop()
        versesQueue = verses
        currentIndex = startIndex
        isPaused = false
        playCurrentVerse()
    }

    private fun playCurrentVerse() {
        if (currentIndex !in versesQueue.indices) {
            stop()
            return
        }
        val verse = versesQueue[currentIndex]
        _ttsState.value = _ttsState.value.copy(
            isSpeaking = true,
            currentVerseIndex = currentIndex,
            currentVerseNumber = verse.verseNumber
        )

        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "verse_$currentIndex")
        val textToSpeak = "Versículo ${verse.verseNumber}: ${verse.text}"
        tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, params, "verse_$currentIndex")
    }

    private fun playNextVerse() {
        if (currentIndex + 1 < versesQueue.size) {
            currentIndex++
            playCurrentVerse()
        } else {
            stop()
        }
    }

    fun pause() {
        isPaused = true
        tts?.stop()
        _ttsState.value = _ttsState.value.copy(isSpeaking = false)
    }

    fun resume() {
        if (isPaused && currentIndex in versesQueue.indices) {
            isPaused = false
            playCurrentVerse()
        }
    }

    fun stop() {
        isPaused = false
        currentIndex = -1
        tts?.stop()
        _ttsState.value = _ttsState.value.copy(
            isSpeaking = false,
            currentVerseIndex = -1,
            currentVerseNumber = -1
        )
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
    }
}
