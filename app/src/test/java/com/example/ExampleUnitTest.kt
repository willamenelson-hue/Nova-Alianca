package com.example

import com.example.data.local.BbcccvvvHelper
import com.example.data.local.BibliaSeedData
import com.example.data.local.entity.Anotacao
import com.example.data.local.entity.Marcacao
import com.example.data.local.entity.ReferenciaCruzada
import com.example.data.local.entity.Versiculo
import com.example.data.model.BibleVersion
import com.example.data.model.GivingType
import com.example.data.model.HymnCollection
import com.example.data.repository.BibleRepository
import com.example.data.repository.ChurchRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testBbcccvvvEncoding_joao316() {
        // João é livro 43, capítulo 3, versículo 16 -> 43003016
        val id = BbcccvvvHelper.toVerseId(43, 3, 16)
        assertEquals(43003016, id)

        assertEquals(43, BbcccvvvHelper.getBookId(id))
        assertEquals(3, BbcccvvvHelper.getChapter(id))
        assertEquals(16, BbcccvvvHelper.getVerseNumber(id))
        assertEquals("João 3:16", BbcccvvvHelper.formatReference(id))
    }

    @Test
    fun testBbcccvvvEncoding_salmo231() {
        // Salmos é livro 19, capítulo 23, versículo 1 -> 19023001
        val id = BbcccvvvHelper.toVerseId(19, 23, 1)
        assertEquals(19023001, id)
        assertEquals("Salmos 23:1", BbcccvvvHelper.formatReference(id))
    }

    @Test
    fun testBibleBooksCatalog_has66Books() {
        val repo = BibleRepository()
        val books = repo.getAllBooks()
        assertEquals(66, books.size)
        assertEquals("Gênesis", books.first().name)
        assertEquals("Apocalipse", books.last().name)
    }

    @Test
    fun testInitialVersiculos_includesAcfJfaaJfac() {
        val versiculos = BibliaSeedData.getInitialVersiculos()
        assertTrue(versiculos.isNotEmpty())

        val versoes = versiculos.map { it.versao }.distinct()
        assertTrue(versoes.contains("ACF"))
        assertTrue(versoes.contains("JFAA"))
        assertTrue(versoes.contains("JFAC"))

        // Verifica João 3:16 nas 3 versões
        val j316Acf = versiculos.firstOrNull { it.id == 43003016 && it.versao == "ACF" }
        val j316Jfaa = versiculos.firstOrNull { it.id == 43003016 && it.versao == "JFAA" }
        val j316Jfac = versiculos.firstOrNull { it.id == 43003016 && it.versao == "JFAC" }

        assertNotNull(j316Acf)
        assertNotNull(j316Jfaa)
        assertNotNull(j316Jfac)
        assertTrue(j316Acf!!.texto.contains("Filho unigênito", ignoreCase = true))
    }

    @Test
    fun testTskCrossReferences_containsJoao316() {
        val referencias = BibliaSeedData.getInitialReferenciasCruzadas()
        assertTrue(referencias.isNotEmpty())

        val joao316Refs = referencias.filter { it.origemVersiculoId == 43003016 }
        assertTrue(joao316Refs.isNotEmpty())
        assertTrue(joao316Refs.any { it.destinoVersiculoId == 45005008 }) // Romanos 5:8
    }

    @Test
    fun testRoomEntitiesCreation() {
        val versiculo = Versiculo(43003016, "ACF", 43, 3, 16, "Porque Deus amou o mundo...")
        assertEquals(43003016, versiculo.id)

        val marcacaoMarcatexto = Marcacao(
            id = 1,
            versiculoId = 43003016,
            tipo = "marcatexto",
            corHex = "#FFF176"
        )
        assertEquals("marcatexto", marcacaoMarcatexto.tipo)

        val marcacaoSublinhado = Marcacao(
            id = 2,
            versiculoId = 43003016,
            tipo = "sublinhado",
            corHex = "#D4AF37"
        )
        assertEquals("sublinhado", marcacaoSublinhado.tipo)

        val anotacao = Anotacao(
            id = 1,
            versiculoId = 43003016,
            textoAnotacao = "Reflexão sobre a magnitude do amor de Deus"
        )
        assertEquals(43003016, anotacao.versiculoId)

        val refCruzada = ReferenciaCruzada(43003016, 45005008, "Amor manifesto")
        assertEquals(43003016, refCruzada.origemVersiculoId)
    }

    @Test
    fun testVersionSwitching() {
        val repo = BibleRepository()
        repo.setBibleVersion(BibleVersion.JFAA)
        assertEquals(BibleVersion.JFAA, repo.currentVersion.value)
    }

    @Test
    fun testPixPayloadGeneration() {
        val churchRepo = ChurchRepository()
        val payload = churchRepo.generatePixPayload(GivingType.DIZIMO, 100.0)
        assertTrue(payload.startsWith("00020126"))
    }

    @Test
    fun testAuthenticBibleContentProvider_noRepetition() {
        val versesAcf = com.example.data.local.BibliaContentProvider.getVersesForChapter(1, 1, "ACF")
        assertTrue(versesAcf.isNotEmpty())
        // Garante que os textos não são idênticos entre si
        val distinctTexts = versesAcf.map { it.texto }.distinct()
        assertEquals(versesAcf.size, distinctTexts.size)

        // Garante que o texto de JFAC difere com grafia histórica do de ACF
        val versesJfac = com.example.data.local.BibliaContentProvider.getVersesForChapter(1, 1, "JFAC")
        assertNotEquals(versesAcf.first().texto, versesJfac.first().texto)
    }
}
