package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.BibliaRoomDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Nova Aliança", appName)

        val churchFullName = context.getString(R.string.church_full_name)
        assertEquals("Igreja Batista Nova Aliança", churchFullName)
        assertNotNull(context.getString(R.string.church_motto))
    }

    @Test
    fun `verify bible database contains authentic non-repeating verses across chapters and versions`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = BibliaRoomDatabase.getDatabase(context)

        // 1. Genesis 1 vs Genesis 2 in ACF
        val gen1Verses = db.versiculoDao().getVersiculosPorCapitulo("ACF", 1, 1).first()
        val gen2Verses = db.versiculoDao().getVersiculosPorCapitulo("ACF", 1, 2).first()

        assertTrue("Genesis 1 deve ter 31 versiculos", gen1Verses.size == 31)
        assertTrue("Genesis 2 deve ter versiculos", gen2Verses.isNotEmpty())

        val gen1v1 = gen1Verses.first().texto
        val gen2v1 = gen2Verses.first().texto

        assertTrue("Gen 1:1 deve ser autêntico", gen1v1.contains("No princípio criou Deus"))
        assertTrue("Gen 2:1 deve ser autêntico", gen2v1.contains("Assim os céus"))
        assertNotEquals("Textos de capítulos diferentes JAMAIS devem ser iguais!", gen1v1, gen2v1)

        // 2. Salmos 23
        val sl23Verses = db.versiculoDao().getVersiculosPorCapitulo("ACF", 19, 23).first()
        assertEquals(6, sl23Verses.size)
        assertTrue(sl23Verses.first().texto.contains("O Senhor é o meu pastor"))

        // 3. João 3:16
        val jo3v16 = db.versiculoDao().getVersiculoPorId("ACF", 43003016)
        assertNotNull(jo3v16)
        assertTrue(jo3v16!!.texto.contains("Porque Deus amou o mundo"))

        // 4. Referências cruzadas TSK para João 3:16
        val refs = db.referenciaCruzadaDao().getReferenciasCruzadas(43003016).first()
        assertTrue("Deve conter referências cruzadas TSK para João 3:16", refs.isNotEmpty())
    }
}
