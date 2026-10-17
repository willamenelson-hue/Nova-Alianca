package com.example.data.local

/**
 * Utilitário para o padrão universal da indústria BBCCCVVV:
 * BB: Livro (01 = Gênesis, 40 = Mateus, 66 = Apocalipse)
 * CCC: Capítulo (001 a 150)
 * VVV: Versículo (001 a 176)
 *
 * Exemplo: João 3:16 -> 43003016
 */
object BbcccvvvHelper {

    fun toVerseId(book: Int, chapter: Int, verse: Int): Int {
        return (book * 1_000_000) + (chapter * 1_000) + verse
    }

    fun getBookId(verseId: Int): Int {
        return verseId / 1_000_000
    }

    fun getChapter(verseId: Int): Int {
        return (verseId % 1_000_000) / 1_000
    }

    fun getVerseNumber(verseId: Int): Int {
        return verseId % 1_000
    }

    fun getChapterMinId(book: Int, chapter: Int): Int {
        return toVerseId(book, chapter, 1)
    }

    fun getChapterMaxId(book: Int, chapter: Int): Int {
        return toVerseId(book, chapter, 999)
    }

    fun formatReference(verseId: Int): String {
        val bookId = getBookId(verseId)
        val chapter = getChapter(verseId)
        val verse = getVerseNumber(verseId)
        val bookName = getBookName(bookId)
        return "$bookName $chapter:$verse"
    }

    fun getBookName(bookId: Int): String {
        return bookNamesMap[bookId] ?: "Livro $bookId"
    }

    val bookNamesMap = mapOf(
        1 to "Gênesis", 2 to "Êxodo", 3 to "Levítico", 4 to "Números", 5 to "Deuteronômio",
        6 to "Josué", 7 to "Juízes", 8 to "Rute", 9 to "1 Samuel", 10 to "2 Samuel",
        11 to "1 Reis", 12 to "2 Reis", 13 to "1 Crônicas", 14 to "2 Crônicas", 15 to "Esdras",
        16 to "Neemias", 17 to "Ester", 18 to "Jó", 19 to "Salmos", 20 to "Provérbios",
        21 to "Eclesiastes", 22 to "Cantares", 23 to "Isaías", 24 to "Jeremias", 25 to "Lamentações",
        26 to "Ezequiel", 27 to "Daniel", 28 to "Oseias", 29 to "Joel", 30 to "Amós",
        31 to "Obadias", 32 to "Jonas", 33 to "Miqueias", 34 to "Naum", 35 to "Habacuque",
        36 to "Sofonias", 37 to "Ageu", 38 to "Zacarias", 39 to "Malaquias",
        40 to "Mateus", 41 to "Marcos", 42 to "Lucas", 43 to "João", 44 to "Atos",
        45 to "Romanos", 46 to "1 Coríntios", 47 to "2 Coríntios", 48 to "Gálatas", 49 to "Efésios",
        50 to "Filipenses", 51 to "Colossenses", 52 to "1 Tessalonicenses", 53 to "2 Tessalonicenses",
        54 to "1 Timóteo", 55 to "2 Timóteo", 56 to "Tito", 57 to "Filemom", 58 to "Hebreus",
        59 to "Tiago", 60 to "1 Pedro", 61 to "2 Pedro", 62 to "1 João", 63 to "2 João",
        64 to "3 João", 65 to "Judas", 66 to "Apocalipse"
    )
}
