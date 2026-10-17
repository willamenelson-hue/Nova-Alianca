package com.example.data.repository

import android.content.Context
import com.example.data.local.BbcccvvvHelper
import com.example.data.local.BibliaContentProvider
import com.example.data.local.BibliaRoomDatabase
import com.example.data.local.BibliaSeedData
import com.example.data.local.PreloadedData
import com.example.data.local.entity.Anotacao
import com.example.data.local.entity.Marcacao
import com.example.data.local.entity.ReferenciaCruzada
import com.example.data.local.entity.Versiculo
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BibleRepository(context: Context? = null) {

    private val database: BibliaRoomDatabase? = context?.let { BibliaRoomDatabase.getDatabase(it) }

    private val _currentVersion = MutableStateFlow(BibleVersion.ACF)
    val currentVersion: StateFlow<BibleVersion> = _currentVersion.asStateFlow()

    private val _completedPlanDays = MutableStateFlow<Set<String>>(
        setOf("plan_nt_90_1", "plan_nt_90_2", "plan_nt_90_3", "plan_psalms_proverbs_30_1")
    )
    val completedPlanDays: StateFlow<Set<String>> = _completedPlanDays.asStateFlow()

    init {
        // Inicializa e limpa registros corrompidos/repetitivos antigos do SQLite
        if (database != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Remove placeholders antigos repetitivos que possam estar em cache no celular
                    database.versiculoDao().deletePlaceholders()

                    val count = database.versiculoDao().countVersiculos("ACF")
                    if (count < 20) {
                        database.versiculoDao().insertVersiculos(BibliaSeedData.getInitialVersiculos())
                        database.referenciaCruzadaDao().insertReferencias(BibliaSeedData.getInitialReferenciasCruzadas())
                    }
                } catch (_: Exception) {
                    // Continua sem falhas se a tabela estiver em criação
                }
            }
        }
    }

    fun getAllBooks(): List<BibleBook> = PreloadedData.allBibleBooks

    fun getBookById(id: Int): BibleBook? = PreloadedData.allBibleBooks.firstOrNull { it.id == id }

    fun setBibleVersion(version: BibleVersion) {
        _currentVersion.value = version
    }

    /**
     * Retorna o Flow reativo dos versículos do capítulo selecionado direto do Room SQLite.
     * Utiliza o BibliaContentProvider para garantir textos bíblicos ricos, autênticos e não repetitivos.
     */
    fun getVersiculosPorCapituloFlow(bookId: Int, chapter: Int, forcedVersionCode: String? = null): Flow<List<Versiculo>> {
        val db = database
        val ver = forcedVersionCode ?: _currentVersion.value.code

        if (db == null) {
            return flowOf(BibliaContentProvider.getVersesForChapter(bookId, chapter, ver))
        }

        return db.versiculoDao().getVersiculosPorCapitulo(ver, bookId, chapter)
            .map { list ->
                if (list.isEmpty()) {
                    val authenticVerses = BibliaContentProvider.getVersesForChapter(bookId, chapter, ver)
                    CoroutineScope(Dispatchers.IO).launch {
                        db.versiculoDao().insertVersiculos(authenticVerses)
                    }
                    authenticVerses
                } else {
                    list
                }
            }
    }

    fun buscarVersiculos(query: String): Flow<List<Versiculo>> {
        val db = database ?: return flowOf(emptyList())
        return db.versiculoDao().buscarVersiculos(_currentVersion.value.code, query)
    }

    suspend fun getVersiculoPorId(id: Int): Versiculo? {
        val db = database ?: return null
        return db.versiculoDao().getVersiculoPorId(_currentVersion.value.code, id)
    }

    // ----------------------------------------------------
    // Marcações (Marca-Texto & Sublinhado) no Room SQLite
    // ----------------------------------------------------
    fun getMarcacoesDoCapitulo(minId: Int, maxId: Int): Flow<List<Marcacao>> {
        val db = database ?: return flowOf(emptyList())
        return db.marcacaoDao().getMarcacoesDoCapitulo(minId, maxId)
    }

    fun getMarcacoesDoVersiculo(versiculoId: Int): Flow<List<Marcacao>> {
        val db = database ?: return flowOf(emptyList())
        return db.marcacaoDao().getMarcacoesDoVersiculo(versiculoId)
    }

    suspend fun salvarMarcacao(versiculoId: Int, tipo: String, corHex: String) {
        val db = database ?: return
        withContext(Dispatchers.IO) {
            db.marcacaoDao().insertOuAtualizarMarcacao(
                Marcacao(
                    versiculoId = versiculoId,
                    tipo = tipo,
                    corHex = corHex
                )
            )
        }
    }

    suspend fun removerMarcacao(versiculoId: Int, tipo: String) {
        val db = database ?: return
        withContext(Dispatchers.IO) {
            db.marcacaoDao().deleteMarcacaoPorVersiculoETipo(versiculoId, tipo)
        }
    }

    suspend fun limparTodasMarcacoesDoVersiculo(versiculoId: Int) {
        val db = database ?: return
        withContext(Dispatchers.IO) {
            db.marcacaoDao().deleteTodasMarcacoesDoVersiculo(versiculoId)
        }
    }

    // ----------------------------------------------------
    // Anotações de Versículos no Room SQLite
    // ----------------------------------------------------
    fun getAnotacoesDoVersiculo(versiculoId: Int): Flow<List<Anotacao>> {
        val db = database ?: return flowOf(emptyList())
        return db.anotacaoDao().getAnotacoesDoVersiculo(versiculoId)
    }

    fun getTodasAnotacoes(): Flow<List<Anotacao>> {
        val db = database ?: return flowOf(emptyList())
        return db.anotacaoDao().getTodasAnotacoes()
    }

    suspend fun salvarAnotacao(versiculoId: Int, texto: String): Long {
        val db = database ?: return 0
        return withContext(Dispatchers.IO) {
            db.anotacaoDao().insertAnotacao(
                Anotacao(
                    versiculoId = versiculoId,
                    textoAnotacao = texto
                )
            )
        }
    }

    suspend fun excluirAnotacao(id: Long) {
        val db = database ?: return
        withContext(Dispatchers.IO) {
            db.anotacaoDao().deleteAnotacaoPorId(id)
        }
    }

    // ----------------------------------------------------
    // Referências Cruzadas TSK (Treasury of Scripture Knowledge)
    // ----------------------------------------------------
    fun getOrigemIdsComReferenciasNoCapitulo(minId: Int, maxId: Int): Flow<List<Int>> {
        val db = database ?: return flowOf(emptyList())
        return db.referenciaCruzadaDao().getOrigemIdsComReferenciasNoCapitulo(minId, maxId)
    }

    fun getReferenciasCruzadasComTexto(versiculoId: Int): Flow<List<ReferenciaComTexto>> {
        val db = database ?: return flowOf(emptyList())
        val ver = _currentVersion.value.code

        return db.referenciaCruzadaDao().getReferenciasCruzadas(versiculoId)
            .map { refs ->
                if (refs.isEmpty()) return@map emptyList()
                val targetIds = refs.map { it.destinoVersiculoId }
                val targetVerses = db.versiculoDao().getVersiculosPorIds(ver, targetIds).associateBy { it.id }

                refs.map { ref ->
                    val refFormatada = BbcccvvvHelper.formatReference(ref.destinoVersiculoId)
                    val textoDestino = targetVerses[ref.destinoVersiculoId]?.texto
                        ?: "Texto de referência cruzada interligada em $refFormatada."

                    ReferenciaComTexto(
                        origemVersiculoId = ref.origemVersiculoId,
                        destinoVersiculoId = ref.destinoVersiculoId,
                        referenciaFormatada = refFormatada,
                        textoDestino = textoDestino,
                        contextoDescricao = ref.contextoDescricao
                    )
                }
            }
    }

    // ----------------------------------------------------
    // Planos de Leitura
    // ----------------------------------------------------
    fun togglePlanDay(planId: String, dayNumber: Int) {
        val key = "${planId}_$dayNumber"
        val current = _completedPlanDays.value.toMutableSet()
        if (current.contains(key)) {
            current.remove(key)
        } else {
            current.add(key)
        }
        _completedPlanDays.value = current
    }

    fun isPlanDayCompleted(planId: String, dayNumber: Int): Boolean {
        return _completedPlanDays.value.contains("${planId}_$dayNumber")
    }
}
