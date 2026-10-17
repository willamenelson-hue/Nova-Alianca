package com.example.data.local

import com.example.data.model.*

object PreloadedData {

    val allBibleBooks = listOf(
        // Antigo Testamento - Pentateuco
        BibleBook(1, "Gênesis", "Gn", Testament.OLD, 50, "Pentateuco"),
        BibleBook(2, "Êxodo", "Êx", Testament.OLD, 40, "Pentateuco"),
        BibleBook(3, "Levítico", "Lv", Testament.OLD, 27, "Pentateuco"),
        BibleBook(4, "Números", "Nm", Testament.OLD, 36, "Pentateuco"),
        BibleBook(5, "Deuteronômio", "Dt", Testament.OLD, 34, "Pentateuco"),

        // Históricos
        BibleBook(6, "Josué", "Js", Testament.OLD, 24, "Históricos"),
        BibleBook(7, "Juízes", "Jz", Testament.OLD, 21, "Históricos"),
        BibleBook(8, "Rute", "Rt", Testament.OLD, 4, "Históricos"),
        BibleBook(9, "1 Samuel", "1Sm", Testament.OLD, 31, "Históricos"),
        BibleBook(10, "2 Samuel", "2Sm", Testament.OLD, 24, "Históricos"),
        BibleBook(11, "1 Reis", "1Rs", Testament.OLD, 22, "Históricos"),
        BibleBook(12, "2 Reis", "2Rs", Testament.OLD, 25, "Históricos"),
        BibleBook(13, "1 Crônicas", "1Cr", Testament.OLD, 29, "Históricos"),
        BibleBook(14, "2 Crônicas", "2Cr", Testament.OLD, 36, "Históricos"),
        BibleBook(15, "Esdras", "Ed", Testament.OLD, 10, "Históricos"),
        BibleBook(16, "Neemias", "Ne", Testament.OLD, 13, "Históricos"),
        BibleBook(17, "Ester", "Et", Testament.OLD, 10, "Históricos"),

        // Poéticos e Sabedoria
        BibleBook(18, "Jó", "Jó", Testament.OLD, 42, "Poéticos"),
        BibleBook(19, "Salmos", "Sl", Testament.OLD, 150, "Poéticos"),
        BibleBook(20, "Provérbios", "Pv", Testament.OLD, 31, "Poéticos"),
        BibleBook(21, "Eclesiastes", "Ec", Testament.OLD, 12, "Poéticos"),
        BibleBook(22, "Cantares", "Ct", Testament.OLD, 8, "Poéticos"),

        // Profetas Maiores
        BibleBook(23, "Isaías", "Is", Testament.OLD, 66, "Profetas Maiores"),
        BibleBook(24, "Jeremias", "Jr", Testament.OLD, 52, "Profetas Maiores"),
        BibleBook(25, "Lamentações", "Lm", Testament.OLD, 5, "Profetas Maiores"),
        BibleBook(26, "Ezequiel", "Ez", Testament.OLD, 48, "Profetas Maiores"),
        BibleBook(27, "Daniel", "Dn", Testament.OLD, 12, "Profetas Maiores"),

        // Profetas Menores
        BibleBook(28, "Oseias", "Os", Testament.OLD, 14, "Profetas Menores"),
        BibleBook(29, "Joel", "Jl", Testament.OLD, 3, "Profetas Menores"),
        BibleBook(30, "Amós", "Am", Testament.OLD, 9, "Profetas Menores"),
        BibleBook(31, "Obadias", "Ob", Testament.OLD, 1, "Profetas Menores"),
        BibleBook(32, "Jonas", "Jn", Testament.OLD, 4, "Profetas Menores"),
        BibleBook(33, "Miqueias", "Mq", Testament.OLD, 7, "Profetas Menores"),
        BibleBook(34, "Naum", "Na", Testament.OLD, 3, "Profetas Menores"),
        BibleBook(35, "Habacuque", "Hc", Testament.OLD, 3, "Profetas Menores"),
        BibleBook(36, "Sofonias", "Sf", Testament.OLD, 3, "Profetas Menores"),
        BibleBook(37, "Ageu", "Ag", Testament.OLD, 2, "Profetas Menores"),
        BibleBook(38, "Zacarias", "Zc", Testament.OLD, 14, "Profetas Menores"),
        BibleBook(39, "Malaquias", "Ml", Testament.OLD, 4, "Profetas Menores"),

        // Novo Testamento - Evangelhos
        BibleBook(40, "Mateus", "Mt", Testament.NEW, 28, "Evangelhos"),
        BibleBook(41, "Marcos", "Mc", Testament.NEW, 16, "Evangelhos"),
        BibleBook(42, "Lucas", "Lc", Testament.NEW, 24, "Evangelhos"),
        BibleBook(43, "João", "Jo", Testament.NEW, 21, "Evangelhos"),

        // Histórico do NT
        BibleBook(44, "Atos", "At", Testament.NEW, 28, "Histórico"),

        // Epístolas Paulinas
        BibleBook(45, "Romanos", "Rm", Testament.NEW, 16, "Cartas Paulinas"),
        BibleBook(46, "1 Coríntios", "1Co", Testament.NEW, 16, "Cartas Paulinas"),
        BibleBook(47, "2 Coríntios", "2Co", Testament.NEW, 13, "Cartas Paulinas"),
        BibleBook(48, "Gálatas", "Gl", Testament.NEW, 6, "Cartas Paulinas"),
        BibleBook(49, "Efésios", "Ef", Testament.NEW, 6, "Cartas Paulinas"),
        BibleBook(50, "Filipenses", "Fp", Testament.NEW, 4, "Cartas Paulinas"),
        BibleBook(51, "Colossenses", "Cl", Testament.NEW, 4, "Cartas Paulinas"),
        BibleBook(52, "1 Tessalonicenses", "1Ts", Testament.NEW, 5, "Cartas Paulinas"),
        BibleBook(53, "2 Tessalonicenses", "2Ts", Testament.NEW, 3, "Cartas Paulinas"),
        BibleBook(54, "1 Timóteo", "1Tm", Testament.NEW, 6, "Cartas Paulinas"),
        BibleBook(55, "2 Timóteo", "2Tm", Testament.NEW, 4, "Cartas Paulinas"),
        BibleBook(56, "Tito", "Tt", Testament.NEW, 3, "Cartas Paulinas"),
        BibleBook(57, "Filemom", "Fm", Testament.NEW, 1, "Cartas Paulinas"),

        // Epístolas Gerais
        BibleBook(58, "Hebreus", "Hb", Testament.NEW, 13, "Cartas Gerais"),
        BibleBook(59, "Tiago", "Tg", Testament.NEW, 5, "Cartas Gerais"),
        BibleBook(60, "1 Pedro", "1Pe", Testament.NEW, 5, "Cartas Gerais"),
        BibleBook(61, "2 Pedro", "2Pe", Testament.NEW, 3, "Cartas Gerais"),
        BibleBook(62, "1 João", "1Jo", Testament.NEW, 5, "Cartas Gerais"),
        BibleBook(63, "2 João", "2Jo", Testament.NEW, 1, "Cartas Gerais"),
        BibleBook(64, "3 João", "3Jo", Testament.NEW, 1, "Cartas Gerais"),
        BibleBook(65, "Judas", "Jd", Testament.NEW, 1, "Cartas Gerais"),

        // Profético do NT
        BibleBook(66, "Apocalipse", "Ap", Testament.NEW, 22, "Profético")
    )

    // Textos Bíblicos Autênticos ACF (Almeida Corrigida Fiel)
    val curatedVersesAcf: Map<String, List<Verse>> = mapOf(
        // Salmos 23
        "19_23" to listOf(
            Verse(19, "Salmos", 23, 1, "O Senhor é o meu pastor, nada me faltará.", "ACF"),
            Verse(19, "Salmos", 23, 2, "Deitar-me faz em verdes pastos, guia-me mansamente a águas tranqüilas.", "ACF"),
            Verse(19, "Salmos", 23, 3, "Refrigera a minha alma; guia-me pelas veredas da justiça, por amor do seu nome.", "ACF"),
            Verse(19, "Salmos", 23, 4, "Ainda que eu andasse pelo vale da sombra da morte, não temeria mal algum, porque tu estás comigo; a tua vara e o teu cajado me consolam.", "ACF"),
            Verse(19, "Salmos", 23, 5, "Preparas uma mesa perante mim na presença dos meus inimigos, unges a minha cabeça com óleo, o meu cálice transborda.", "ACF"),
            Verse(19, "Salmos", 23, 6, "Certamente que a bondade e a misericórdia me seguirão todos os dias da minha vida; e habitarei na casa do Senhor por longos dias.", "ACF")
        ),

        // Salmos 91
        "19_91" to listOf(
            Verse(19, "Salmos", 91, 1, "Aquele que habita no esconderijo do Altíssimo, à sombra do Onipotente descansará.", "ACF"),
            Verse(19, "Salmos", 91, 2, "Direi do Senhor: Ele é o meu Deus, o meu refúgio, a minha fortaleza, e nele confiarei.", "ACF"),
            Verse(19, "Salmos", 91, 3, "Porque ele te livrará do laço do passarinheiro, e da peste perniciosa.", "ACF"),
            Verse(19, "Salmos", 91, 4, "Ele te cobrirá com as suas penas, e debaixo das suas asas te confiarás; a sua verdade será o teu escudo e broquel.", "ACF"),
            Verse(19, "Salmos", 91, 5, "Não terás medo do terror de noite nem da seta que voa de dia.", "ACF"),
            Verse(19, "Salmos", 91, 6, "Nem da peste que anda na escuridão, nem da mortandade que assola ao meio-dia.", "ACF"),
            Verse(19, "Salmos", 91, 7, "Mil cairão ao teu lado, e dez mil à tua direita, mas não chegará a ti.", "ACF")
        ),

        // Salmos 121
        "19_121" to listOf(
            Verse(19, "Salmos", 121, 1, "Levantarei os meus olhos para os montes, de onde vem o meu socorro.", "ACF"),
            Verse(19, "Salmos", 121, 2, "O meu socorro vem do Senhor que fez o céu e a terra.", "ACF"),
            Verse(19, "Salmos", 121, 3, "Não deixará vacilar o teu pé; aquele que te guarda não tosquenejará.", "ACF"),
            Verse(19, "Salmos", 121, 4, "Eis que não tosquenejará nem dormirá o guarda de Israel.", "ACF"),
            Verse(19, "Salmos", 121, 5, "O Senhor é quem te guarda; o Senhor é a tua sombra à tua direita.", "ACF")
        ),

        // João 1
        "43_1" to listOf(
            Verse(43, "João", 1, 1, "No princípio era o Verbo, e o Verbo estava com Deus, e o Verbo era Deus.", "ACF"),
            Verse(43, "João", 1, 2, "Ele estava no princípio com Deus.", "ACF"),
            Verse(43, "João", 1, 3, "Todas as coisas foram feitas por ele, e sem ele nada do que foi feito se fez.", "ACF"),
            Verse(43, "João", 1, 4, "Nele estava a vida, e a vida era a luz dos homens.", "ACF"),
            Verse(43, "João", 1, 14, "E o Verbo se fez carne, e habitou entre nós, e vimos a sua glória, como a glória do unigênito do Pai, cheio de graça e de verdade.", "ACF")
        ),

        // João 3
        "43_3" to listOf(
            Verse(43, "João", 3, 16, "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna.", "ACF"),
            Verse(43, "João", 3, 17, "Porque Deus enviou o seu Filho ao mundo, não para que condenasse o mundo, mas para que o mundo fosse salvo por ele.", "ACF")
        ),

        // Mateus 6
        "40_6" to listOf(
            Verse(40, "Mateus", 6, 9, "Portanto, vós orareis assim: Pai nosso, que estás nos céus, santificado seja o teu nome;", "ACF"),
            Verse(40, "Mateus", 6, 10, "Venha o teu reino, seja feita a tua vontade, assim na terra como no céu;", "ACF"),
            Verse(40, "Mateus", 6, 11, "O pão nosso de cada dia nos dá hoje;", "ACF"),
            Verse(40, "Mateus", 6, 12, "E perdoa-nos as nossas dívidas, assim como nós perdoamos aos nossos devedores;", "ACF"),
            Verse(40, "Mateus", 6, 13, "E não nos induzas à tentação; mas livra-nos do mal; porque teu é o reino, e o poder, e a glória, para sempre. Amém.", "ACF"),
            Verse(40, "Mateus", 6, 33, "Mas, buscai primeiro o reino de Deus, e a sua justiça, e todas estas coisas vos serão acrescentadas.", "ACF"),
            Verse(40, "Mateus", 6, 34, "Não vos inquieteis, pois, pelo dia de amanhã, porque o dia de amanhã cuidará de si mesmo. Basta a cada dia o seu mal.", "ACF")
        ),

        // Filipenses 4
        "50_4" to listOf(
            Verse(50, "Filipenses", 4, 4, "Regozijai-vos sempre no Senhor; outra vez digo, regozijai-vos.", "ACF"),
            Verse(50, "Filipenses", 4, 6, "Não estejais inquietos por coisa alguma; antes as vossas petições sejam em tudo conhecidas diante de Deus pela oração e súplica, com ação de graças.", "ACF"),
            Verse(50, "Filipenses", 4, 7, "E a paz de Deus, que excede todo o entendimento, guardará os vossos corações e os vossos sentimentos em Cristo Jesus.", "ACF"),
            Verse(50, "Filipenses", 4, 13, "Posso todas as coisas em Cristo que me fortalece.", "ACF"),
            Verse(50, "Filipenses", 4, 19, "O meu Deus, segundo as suas riquezas, suprirá todas as vossas necessidades em glória, por Cristo Jesus.", "ACF")
        ),

        // Romanos 8
        "45_8" to listOf(
            Verse(45, "Romanos", 8, 28, "E sabemos que todas as coisas concorrem para o bem daqueles que amam a Deus, daqueles que são chamados segundo o seu propósito.", "ACF"),
            Verse(45, "Romanos", 8, 31, "Que diremos, pois, a estas coisas? Se Deus é por nós, quem será contra nós?", "ACF"),
            Verse(45, "Romanos", 8, 37, "Mas em todas estas coisas somos mais do que vencedores, por aquele que nos amou.", "ACF"),
            Verse(45, "Romanos", 8, 38, "Porque estou certo de que, nem a morte, nem a vida, nem os anjos, nem os principados, nem as potestades, nem o presente, nem o porvir,", "ACF"),
            Verse(45, "Romanos", 8, 39, "Nem a altura, nem a profundidade, nem alguma outra criatura nos poderá separar do amor de Deus, que está em Cristo Jesus nosso Senhor.", "ACF")
        ),

        // Provérbios 3
        "20_3" to listOf(
            Verse(20, "Provérbios", 3, 5, "Confia no Senhor de todo o teu coração, e não te estribes no teu próprio entendimento.", "ACF"),
            Verse(20, "Provérbios", 3, 6, "Reconhece-o em todos os teus caminhos, e ele endireitará as tuas veredas.", "ACF"),
            Verse(20, "Provérbios", 3, 9, "Honra ao Senhor com os teus bens, e com a primeira parte de todos os teus frutos;", "ACF"),
            Verse(20, "Provérbios", 3, 10, "E se encherão os teus celeiros abundantemente, e trasbordarão de mosto os teus lagares.", "ACF")
        ),

        // Isaías 40
        "23_40" to listOf(
            Verse(23, "Isaías", 40, 29, "Dá força ao cansado, e multiplica as forças ao que não tem nenhum vigor.", "ACF"),
            Verse(23, "Isaías", 40, 31, "Mas os que esperam no Senhor renovarão as forças, subirão com asas como águias; correrão, e não se cansarão; caminharão, e não se fatigarão.", "ACF")
        )
    )

    // Versículo do Dia
    val dailyVerses = listOf(
        Verse(50, "Filipenses", 4, 13, "Posso todas as coisas em Cristo que me fortalece.", "ACF"),
        Verse(19, "Salmos", 23, 1, "O Senhor é o meu pastor, nada me faltará.", "ACF"),
        Verse(43, "João", 3, 16, "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna.", "ACF"),
        Verse(20, "Provérbios", 3, 5, "Confia no Senhor de todo o teu coração, e não te estribes no teu próprio entendimento.", "ACF"),
        Verse(45, "Romanos", 8, 28, "E sabemos que todas as coisas concorrem para o bem daqueles que amam a Deus, daqueles que são chamados segundo o seu propósito.", "ACF"),
        Verse(23, "Isaías", 40, 31, "Mas os que esperam no Senhor renovarão as forças, subirão com asas como águias; correrão, e não se cansarão; caminharão, e não se fatigarão.", "ACF"),
        Verse(19, "Salmos", 121, 2, "O meu socorro vem do Senhor que fez o céu e a terra.", "ACF"),
        Verse(40, "Mateus", 6, 33, "Mas, buscai primeiro o reino de Deus, e a sua justiça, e todas estas coisas vos serão acrescentadas.", "ACF"),
        Verse(45, "Romanos", 8, 31, "Que diremos, pois, a estas coisas? Se Deus é por nós, quem será contra nós?", "ACF"),
        Verse(19, "Salmos", 91, 1, "Aquele que habita no esconderijo do Altíssimo, à sombra do Onipotente descansará.", "ACF")
    )

    // Hinário: Harpa Cristã
    val harpaCristaHymns = listOf(
        Hymn(
            number = 1,
            title = "Chuvas de Graça",
            collection = HymnCollection.HARPA_CRISTA,
            author = "James McGranahan",
            lyrics = """1. Deus prometeu com certeza
Chuvas de graça mandar;
Ele nos dá fortaleza,
E ricas bênçãos sem par.

Coro:
Chuvas de graça,
Chuvas pedimos, Senhor;
Manda-nos já tempestades,
Gotas benditas de amor.

2. Cristo nos dá a promessa
Do Santo Consolador;
Dando-nos paz e alegria,
Cantemos com grande ardor.

3. Cristo nos dá a vitória,
Graça teremos no fim;
Para vivermos na glória,
Com nosso Deus e Benquim.""",
            chorus = "Chuvas de graça, chuvas pedimos, Senhor; manda-nos já tempestades, gotas benditas de amor."
        ),
        Hymn(
            number = 15,
            title = "Foi na Cruz",
            collection = HymnCollection.HARPA_CRISTA,
            author = "Ralph E. Hudson",
            lyrics = """1. Oh! Quão cego eu andei e perdido vaguei,
Longe, longe do meu Salvador!
Mas do céu Ele desceu e Seu sangue verteu
Pra salvar um tão pobre pecador.

Coro:
Foi na cruz, foi na cruz onde um dia eu vi
Meu pecado castigado em Jesus;
Foi ali, pela fé, que os olhos abri,
E agora me alegro em Sua luz.

2. Eu ouvia falar dessa graça sem par,
Que do céu trouxe nosso Jesus;
Mas eu surdo me fiz, converter-me não quis
Ao Senhor que por mim morreu na cruz.

3. Mas um dia senti meu pecado e temi,
Sobre mim o furor da lei;
Foi então que eu olhei para a cruz e vi
O perdão que em Jesus alcancei.""",
            chorus = "Foi na cruz, foi na cruz onde um dia eu vi meu pecado castigado em Jesus; foi ali pela fé que os olhos abri, e agora me alegro em Sua luz."
        ),
        Hymn(
            number = 186,
            title = "De Valor em Valor",
            collection = HymnCollection.HARPA_CRISTA,
            author = "Tradicional",
            lyrics = """1. Pela fé que uma vez me foi dada,
Pra seguir o Cordeiro de Deus,
Pela graça de Deus derramada,
Andarei com Jesus para os céus.

Coro:
De valor em valor e de fé em mais fé,
Seguirei ao meu bom Salvador;
Ele me susterá, firme Nele de pé,
Com poder, paciência e amor.

2. Se a luta se torna mais forte,
Não terei nenhum medo do mal;
Pois Jesus triunfou sobre a morte,
E nos deu galardão eternal.""",
            chorus = "De valor em valor e de fé em mais fé, seguirei ao meu bom Salvador; Ele me susterá, firme Nele de pé, com poder, paciência e amor."
        ),
        Hymn(
            number = 545,
            title = "Porque Ele Vive",
            collection = HymnCollection.HARPA_CRISTA,
            author = "Bill & Gloria Gaither",
            lyrics = """1. Deus enviou Seu Filho amado
Para salvar e perdoar;
Na cruz morreu por meu pecado,
Mas ressurgiu e vivo com o Pai está.

Coro:
Porque Ele vive, posso crer no amanhã.
Porque Ele vive, temor não há.
Mas eu bem sei, eu sei que a minha vida
Está nas mãos do meu Jesus, que vivo está.

2. E quando, enfim, chegar a hora
Em que a morte enfrentarei,
Sem medo, então, terei vitória:
Verei na glória o meu Jesus que vivo está!""",
            chorus = "Porque Ele vive, posso crer no amanhã. Porque Ele vive, temor não há. Mas eu bem sei que a minha vida está nas mãos do meu Jesus que vivo está."
        )
    )

    // Hinário: Cantor Cristão (Hinário Histórico Batista)
    val cantorCristaoHymns = listOf(
        Hymn(
            number = 1,
            title = "A Deus Demos Glória",
            collection = HymnCollection.CANTOR_CRISTAO,
            author = "Fanny Crosby / William Doane",
            lyrics = """1. A Deus demos glória com grande fervor,
Seu Filho bendito por nós todos deu;
A graça expiatória nos deu o Senhor,
Quem crer em Jesus tem entrada no céu.

Coro:
Exultai! Exultai! Vinde todos louvar
A Jesus, Salvador, a Jesus, Redentor!
A Deus demos glória, porquanto do céu
Seu Filho bendito por nós todos deu.

2. Remidos nós somos com sangue real,
A todos concede o perdão divinal;
O mais pecador que a Jesus recorrer,
A vida eterna vai logo obter.""",
            chorus = "Exultai! Exultai! Vinde todos louvar a Jesus Salvador, a Jesus Redentor! A Deus demos glória porquanto do céu Seu Filho bendito por nós todos deu."
        ),
        Hymn(
            number = 15,
            title = "Segurança",
            collection = HymnCollection.CANTOR_CRISTAO,
            author = "Fanny Crosby / Phoebe Knapp",
            lyrics = """1. Que segurança tenho em Jesus!
Pois Ele é a minha salvação e luz;
Comprado fui por Seu grande amor,
Nascido de novo no Redentor.

Coro:
Canta, minha alma! Canta ao Senhor!
Rende-Lhe sempre glória e louvor!
Canta, minha alma! Canta ao Senhor!
Rende-Lhe sempre glória e louvor!

2. Inteiramente me entrego a Ti,
Pois Tua graça opera em mim;
Anjos descendo trazem dos céus
Ecos de glória do meu bom Deus.""",
            chorus = "Canta minha alma! Canta ao Senhor! Rende-Lhe sempre glória e louvor!"
        ),
        Hymn(
            number = 143,
            title = "Castelo Forte",
            collection = HymnCollection.CANTOR_CRISTAO,
            author = "Martinho Lutero (1529)",
            lyrics = """1. Castelo forte é nosso Deus,
Espada e bom escudo;
Com Seu poder defende os Seus
Em todo transe agudo.
Com fúria e com ardil,
Nos cerca o diabo vil;
Com armas e furor,
Igual não há na terra.

2. A nossa força nada faz,
Estamos, sim, perdidos;
Mas nosso Deus socorro traz,
E somos protegidos.
Sabeis quem é Jesus?
O que venceu na cruz,
Senhor dos altos céus,
Triunfará na guerra!""",
            chorus = null
        ),
        Hymn(
            number = 188,
            title = "Tudo Entregarei",
            collection = HymnCollection.CANTOR_CRISTAO,
            author = "Judson W. Van DeVenter",
            lyrics = """1. Tudo, ó Cristo, a Ti entrego,
Tudo, sim, por Ti darei!
Resoluto, mas submisso,
Sempre a Ti eu seguirei.

Coro:
Tudo entregarei!
Tudo entregarei!
Sim, por Ti, Jesus bendito,
Tudo deixarei!

2. Tudo, ó Cristo, a Ti entrego,
Corpo e alma, mente e ser!
Que o Teu Santo Espírito desça,
Dando graça e poder.""",
            chorus = "Tudo entregarei! Tudo entregarei! Sim, por Ti Jesus bendito, tudo deixarei!"
        )
    )

    // Músicas Gospel Contemporâneas e Letras.mus.br
    val gospelSongs = listOf(
        GospelSong(
            id = "gospel_1",
            title = "Grandes Coisas",
            artist = "Fernandinho",
            lyricsUrl = "https://www.letras.mus.br/fernandinho/1601051/",
            lyricsSnippet = "Tu és o Deus dessa cidade, Tu és o Rei deste povo...",
            fullLyrics = """Tu és o Deus dessa cidade
Tu és o Rei deste povo
Tu és o Senhor desta nação
Tu és!

Esperança para os perdidos
Tu és a paz pros cansados
Tu és o amor aos desamparados
Tu és!

Não há outro como Tu!
Não há outro como Tu!

Grandes coisas vão acontecer aqui
Grandes coisas vão acontecer neste lugar!"""
        ),
        GospelSong(
            id = "gospel_2",
            title = "Lugar Secreto",
            artist = "Gabriela Rocha",
            lyricsUrl = "https://www.letras.mus.br/gabriela-rocha/lugar-secreto/",
            lyricsSnippet = "Tu és tudo o que eu mais quero, o meu fôlego, como eu Te amo...",
            fullLyrics = """Tu és tudo o que eu mais quero
O meu fôlego, como eu Te amo
Faz meu coração queimar de amor por Ti

Eu quero Te conhecer, me aproximar de Ti
Ouvir a Tua voz, no Teu lugar secreto
E me derramar aos Teus pés

Tudo o que eu preciso está em Ti
Eu me rendo, me prostro diante do Rei!"""
        ),
        GospelSong(
            id = "gospel_3",
            title = "A Bênção (The Blessing)",
            artist = "Gabriel Guedes & Nívea Soares",
            lyricsUrl = "https://www.letras.mus.br/gabriel-guedes/a-bencao/",
            lyricsSnippet = "O Senhor te abençoe e te guarde, o Senhor faça resplandecer o Seu rosto sobre ti...",
            fullLyrics = """O Senhor te abençoe e te guarde
O Senhor faça resplandecer o Seu rosto sobre ti
E tenha misericórdia de ti

O Senhor sobre ti levante o Seu rosto
E te dê a paz!

Amém, amém, amém!
Amém, amém, amém!

Que a Sua presença vá contigo
E a Sua graça sobre a tua família
Por mil gerações!"""
        ),
        GospelSong(
            id = "gospel_4",
            title = "É Tudo Sobre Você",
            artist = "Morada",
            lyricsUrl = "https://www.letras.mus.br/morada/e-tudo-sobre-voce/",
            lyricsSnippet = "Não há ninguém como nosso Deus, não há outro como Tu...",
            fullLyrics = """É tudo sobre Você, Jesus
E nada sobre nós
Teu nome é exaltado
Teu trono é eterno

Santo, Santo é o Senhor dos Exércitos
Toda a terra está cheia da Tua glória!
Queima o meu coração por Ti!"""
        )
    )

    // Mensagens Pastorais (Pr. Nelson Willame - Igreja Batista Nova Aliança)
    val pastorMessages = listOf(
        PastorMessage(
            id = "msg_1",
            title = "Permanecendo Firmes na Nova Aliança da Graça",
            pastorName = "Pr. Nelson Willame",
            date = "Domingo Passado",
            summary = "Uma palavra pastoral sobre a fidelidade inabalável de Deus nos momentos de provação e recomeço espiritual.",
            fullText = """Irmãos e irmãs da Nova Aliança, a graça do nosso Senhor Jesus Cristo nos alcançou não pelas nossas obras humanas, mas pelo imenso amor do Pai manifestado na cruz. 

Quando enfrentamos o vale das decisões, o desânimo ou os desafios familiares, precisamos lembrar que o Senhor já estabeleceu conosco uma aliança eterna que não se desfaz. 

Como nos ensina o apóstolo Paulo aos Filipenses: 'Aquele que começou boa obra em vós há de completá-la até ao Dia de Cristo Jesus'. Não desfaleçam os vossos corações; busquem a presença do Senhor na oração secreta, no culto comunitário e no amor fraternal. Nossa igreja é um porto de restauração para a sua vida!""",
            scriptureReference = "Filipenses 1:6",
            audioDurationMinutes = 18
        ),
        PastorMessage(
            id = "msg_2",
            title = "O Poder da Oração em Família",
            pastorName = "Pr. Nelson Willame",
            date = "Quarta-Feira de Doutrina",
            summary = "Como edificar o altar de Deus dentro do nosso lar e proteger nossos filhos através da intercessão contínua.",
            fullText = """A família é o primeiro ministério que Deus nos confiou. Em Josué 24:15 está escrito: 'Eu e a minha casa serviremos ao Senhor'. 

Muitas vezes investimos tempo em tantas coisas materiais e nos esquecemos de colocar os joelhos no chão pelos nossos cônjuges e filhos. A oração tem o poder de quebrar correntes de contenda, curar enfermidades e restaurar a paz que excede o entendimento. Dedique hoje 15 minutos para orar com sua família antes de dormir!""",
            scriptureReference = "Josué 24:15",
            audioDurationMinutes = 14
        )
    )

    // Devocional Diário
    val currentDevotional = DevotionalDaily(
        id = "dev_today",
        date = "Hoje, Palavra Viva",
        title = "Paz em Meio à Tempestade",
        keyVerseReference = "Filipenses 4:6-7",
        keyVerseText = "Não estejais inquietos por coisa alguma; antes as vossas petições sejam em tudo conhecidas diante de Deus pela oração e súplica, com ação de graças. E a paz de Deus, que excede todo o entendimento, guardará os vossos corações e os vossos sentimentos em Cristo Jesus.",
        messageContent = """A ansiedade tenta roubar nossa visão espiritual nos fazendo crer que estamos sozinhos diante do mar revolto. No entanto, o convite de Jesus é para depositarmos nEle cada preocupação. Entregar não é ser passivo, é exercer a fé mais ativa: aquela que confia no socorro do Alto.""",
        prayerOfFaith = "Senhor Deus, entrego hoje nas Tuas mãos santas cada incerteza sobre o futuro, a saúde e o sustento da minha família. Enche a minha mente com a Tua paz sobrenatural. Em nome de Jesus, Amém!"
    )

    // Agenda da Igreja Batista Nova Aliança
    val churchEvents = listOf(
        ChurchEvent(
            id = "ev_1",
            title = "Culto de Celebração & Família",
            dayOfWeek = "Domingo",
            time = "19:00",
            location = "Templo Sede - Nave Principal",
            description = "Culto dominical com louvor congregacional, mensagens inspiradoras da Palavra e oração pastoral.",
            ministry = "Pastoral & Louvor"
        ),
        ChurchEvent(
            id = "ev_2",
            title = "Escola Bíblica Dominical (EBD)",
            dayOfWeek = "Domingo",
            time = "09:00",
            location = "Salas de Ensino & Templo",
            description = "Classes por faixa etária: Crianças, Juniores, Jovens e Adultos. Crescimento no conhecimento bíblico.",
            ministry = "Educação Cristã"
        ),
        ChurchEvent(
            id = "ev_3",
            title = "Culto de Oração & Estudo Bíblico",
            dayOfWeek = "Quarta-Feira",
            time = "19:30",
            location = "Templo Sede",
            description = "Momento precioso de intercessão pelos enfermos, famílias e aprendizado expositivo das Escrituras.",
            ministry = "Intercessão & Ensino"
        ),
        ChurchEvent(
            id = "ev_4",
            title = "Culto de Jovens: Aliança Jovem",
            dayOfWeek = "Sábado",
            time = "19:30",
            location = "Auditório dos Jovens",
            description = "Comunhão dinâmica, louvores vibrantes, dinâmicas e pregações focadas nos desafios da juventude cristã.",
            ministry = "Juventude Nova Aliança"
        ),
        ChurchEvent(
            id = "ev_5",
            title = "Reunião de Oração das Mulheres de Fé",
            dayOfWeek = "Terça-Feira",
            time = "15:00",
            location = "Sala de Oração",
            description = "Encontro de clamor e fortalecimento espiritual para mães, esposas e jovens.",
            ministry = "Ministério Feminino"
        )
    )

    // Mural de Oração Inicial
    val initialPrayerRequests = listOf(
        PrayerRequest(
            id = "p_1",
            authorName = "Irmã Maria de Lourdes",
            requestText = "Peço oração pela saúde do meu esposo Roberto, que fará exames médicos nesta semana. Cremos no Deus que cura!",
            category = "Saúde",
            dateFormatted = "Hoje às 10:30",
            prayCount = 28,
            isPrivateToPastor = false
        ),
        PrayerRequest(
            id = "p_2",
            authorName = "Carlos Eduardo (Jovem)",
            requestText = "Intercessão pela aprovação no vestibular e direcionamento profissional para honrar o nome de Jesus.",
            category = "Emprego & Futuro",
            dateFormatted = "Ontem às 20:15",
            prayCount = 19,
            isPrivateToPastor = false
        ),
        PrayerRequest(
            id = "p_3",
            authorName = "Família Silva",
            requestText = "Agradecimento a Deus por um livramento no trânsito e restauração do nosso lar. Toda glória a Ele!",
            category = "Agradecimento",
            dateFormatted = "Há 2 dias",
            prayCount = 42,
            isPrivateToPastor = false
        )
    )

    // Temas do Cotidiano para Planos de Leitura
    val readingThemes = listOf(
        ReadingTheme(
            id = "theme_peace",
            title = "Paz e Vencendo a Ansiedade",
            description = "Passagens bíblicas para tranquilizar a mente, acalmar o coração e confiar no cuidado diário de Deus.",
            iconName = "peace",
            passages = listOf(
                ThemePassage("Filipenses 4:6-7", "Não estejais inquietos por coisa alguma...", "Entregue suas preocupações em oração ao invés de carregá-las sozinho."),
                ThemePassage("Mateus 6:25-34", "Não vos inquieteis, pois, pelo dia de amanhã...", "Deus cuida dos lírios do campo e cuidará muito mais de você."),
                ThemePassage("Salmos 23:1-6", "O Senhor é o meu pastor, nada me faltará...", "Nos vales escuros, a presença do Bom Pastor é o nosso refúgio."),
                ThemePassage("1 Pedro 5:7", "Lançando sobre ele toda a vossa ansiedade, porque ele tem cuidado de vós.", "Deus não é indiferente à sua dor.")
            )
        ),
        ReadingTheme(
            id = "theme_family",
            title = "Família & Relacionamentos Cristãos",
            description = "Sabedoria divina para fortalecer o casamento, educar os filhos e cultivar harmonia dentro do lar.",
            iconName = "family",
            passages = listOf(
                ThemePassage("Josué 24:15", "Eu e a minha casa serviremos ao Senhor.", "O compromisso espiritual do lar começa com decisões de fé diárias."),
                ThemePassage("Efésios 5:22-33", "Vós, maridos, amai vossas mulheres, como também Cristo amou a igreja...", "O amor sacrificial no casamento reflete a aliança de Cristo."),
                ThemePassage("Provérbios 22:6", "Instrui o menino no caminho em que deve andar...", "O exemplo dos pais é o maior legado que os filhos receberão."),
                ThemePassage("Colossenses 3:13", "Suportando-vos uns aos outros, e perdoando-vos uns aos outros...", "O perdão é a base inegociável da convivência familiar.")
            )
        ),
        ReadingTheme(
            id = "theme_finances",
            title = "Finanças sob a Luz Bíblica",
            description = "Princípios bíblicos de generosidade, fidelidade nos dízimos, planejamento e mordomia cristã.",
            iconName = "finance",
            passages = listOf(
                ThemePassage("Malaquias 3:10", "Trazei todos os dízimos à casa do tesouro...", "Fidelidade que abre as janelas dos céus com bênçãos transbordantes."),
                ThemePassage("2 Coríntios 9:6-7", "Cada um contribua segundo propôs no seu coração; não com tristeza, ou por necessidade; porque Deus ama ao que dá com alegria.", "A generosidade reflete o coração gracioso de Deus."),
                ThemePassage("Provérbios 3:9-10", "Honra ao Senhor com os teus bens, e com as primícias de toda a tua renda.", "Colocar a Deus em primeiro lugar em tudo o que conquistamos."),
                ThemePassage("1 Timóteo 6:6-10", "Porque nada trouxemos para este mundo...", "Contentamento e prudência com os recursos materiais.")
            )
        ),
        ReadingTheme(
            id = "theme_hope",
            title = "Esperança & Cura nas Tribulações",
            description = "Palavras de conforto para momentos de perda, enfermidade ou desânimo na jornada.",
            iconName = "hope",
            passages = listOf(
                ThemePassage("Romanos 8:28", "Todas as coisas cooperam para o bem daqueles que amam a Deus.", "Mesmo as aflições presentes têm um propósito eterno de redenção."),
                ThemePassage("Salmos 91:1-2", "Aquele que habita no esconderijo do Altíssimo...", "Debaixo das asas de Deus encontramos fortaleza invencível."),
                ThemePassage("Isaías 40:31", "Os que esperam no Senhor renovarão as suas forças...", "A paciência em Deus transforma fraqueza em vôo de águia."),
                ThemePassage("João 16:33", "No mundo tereis aflições, mas tende bom ânimo, eu venci o mundo.", "A vitória final já foi conquistada pelo Salvador.")
            )
        )
    )

    // Planos de Leitura Estruturados com Checkbox por Dia
    val structuredReadingPlans = listOf(
        ReadingPlan(
            id = "plan_nt_90",
            title = "Novo Testamento em 90 Dias",
            subtitle = "3 capítulos por dia com acompanhamento sistemático",
            durationDays = 90,
            category = "Novo Testamento",
            items = (1..90).map { day ->
                val startChapter = ((day - 1) * 3) + 1
                PlanDayItem(
                    dayNumber = day,
                    readingTarget = "Dia $day: Capítulos $startChapter a ${startChapter + 2}",
                    chaptersCount = 3,
                    description = "Leitura sequencial reflexiva com oração inicial.",
                    isCompleted = day <= 3 // Primeiros 3 dias pré-marcados como demonstração
                )
            }
        ),
        ReadingPlan(
            id = "plan_psalms_proverbs_30",
            title = "Salmos e Provérbios em 30 Dias",
            subtitle = "Sabedoria prática e adoração diária",
            durationDays = 30,
            category = "Devocional",
            items = (1..30).map { day ->
                PlanDayItem(
                    dayNumber = day,
                    readingTarget = "Dia $day: Provérbios $day e Salmos ${(day * 5) - 4} a ${day * 5}",
                    chaptersCount = 6,
                    description = "Um capítulo do livro de sabedoria e cinco salmos de louvor.",
                    isCompleted = day <= 5
                )
            }
        ),
        ReadingPlan(
            id = "plan_bible_year",
            title = "Bíblia Completa em 1 Ano",
            subtitle = "Leitura diária do Antigo e Novo Testamento",
            durationDays = 365,
            category = "Bíblia Completa",
            items = (1..50).map { day ->
                PlanDayItem(
                    dayNumber = day,
                    readingTarget = "Dia $day: 3 caps. AT + 1 cap. NT",
                    chaptersCount = 4,
                    description = "Imersão bíblica diária completa.",
                    isCompleted = day <= 2
                )
            }
        )
    )

    // Locais Bíblicos para o Google Maps
    val biblicalLocations = listOf(
        BiblicalLocation(
            id = "loc_templo_ibna",
            name = "Igreja Batista Nova Aliança (Templo Sede)",
            modernName = "Igreja Batista Nova Aliança",
            latitude = -23.55052,
            longitude = -46.633308,
            scriptureReferences = listOf("Salmos 122:1", "Hebreus 10:25"),
            description = "Nossa comunidade de fé reunida para louvor, celebração, comunhão e pregação do Evangelho da Graça.",
            historicalSignificance = "Lugar de adoração e aliança com o Senhor e de acolhimento para a sua família."
        ),
        BiblicalLocation(
            id = "loc_jerusalem",
            name = "Jerusalém (Cidade Santa)",
            modernName = "Jerusalém, Israel",
            latitude = 31.7683,
            longitude = 35.2137,
            scriptureReferences = listOf("Salmos 122:6", "Lucas 19:41-44", "Atos 1:8"),
            description = "Centro espiritual de Israel, local do Templo de Salomão, da crucificação e ressurreição triunfal de Jesus.",
            historicalSignificance = "Palco do Pentecostes bíblico e do nascimento da Igreja primitiva."
        ),
        BiblicalLocation(
            id = "loc_belem",
            name = "Belém de Judá (Casa do Pão)",
            modernName = "Belém, Cisjordânia",
            latitude = 31.7054,
            longitude = 35.2024,
            scriptureReferences = listOf("Miqueias 5:2", "Mateus 2:1-6", "Lucas 2:4-7"),
            description = "Local de nascimento de Davi e da vinda ao mundo do Messias prometido, Jesus Cristo.",
            historicalSignificance = "Cumprimento das profecias messiânicas sobre o nascimento do Salvador."
        ),
        BiblicalLocation(
            id = "loc_galileia",
            name = "Mar da Galileia (Lago de Genesaré)",
            modernName = "Lago de Tiberíades, Israel",
            latitude = 32.8224,
            longitude = 35.5862,
            scriptureReferences = listOf("Mateus 4:18-22", "Marcos 4:35-41", "João 21:1-14"),
            description = "Local onde Jesus chamou os primeiros pescadores de homens, acalmou a tempestade e andou sobre as águas.",
            historicalSignificance = "Coração geográfico do ministério público e milagres de Jesus."
        ),
        BiblicalLocation(
            id = "loc_nazare",
            name = "Nazaré",
            modernName = "Nazaré, Israel",
            latitude = 32.6996,
            longitude = 35.3035,
            scriptureReferences = listOf("Lucas 1:26-38", "Mateus 2:23", "Lucas 4:16-30"),
            description = "Cidade onde Jesus cresceu em sabedoria, estatura e graça diante de Deus e dos homens.",
            historicalSignificance = "Local da Anunciação à virgem Maria pelo anjo Gabriel."
        ),
        BiblicalLocation(
            id = "loc_sinai",
            name = "Monte Sinai (Monte Horebe)",
            modernName = "Península do Sinai, Egito",
            latitude = 28.5393,
            longitude = 33.9752,
            scriptureReferences = listOf("Êxodo 19:1-25", "Êxodo 20:1-17", "1 Reis 19:8-14"),
            description = "O monte sagrado onde Moisés recebeu as tábuas dos Dez Mandamentos e a Lei de Deus.",
            historicalSignificance = "Estabelecimento da primeira aliança de Deus com o povo de Israel."
        )
    )

    // Quiz Bíblico
    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            question = "Quem foi chamado de 'o amigo de Deus' nas Escrituras?",
            options = listOf("Moisés", "Abraão", "Davi", "Elias"),
            correctIndex = 1,
            biblicalReference = "Tiago 2:23 / Isaías 41:8",
            explanation = "E cumpriu-se a Escritura, que diz: E creu Abraão em Deus, e foi-lhe isso imputado como justiça, e foi chamado o amigo de Deus.",
            difficulty = "Iniciante"
        ),
        QuizQuestion(
            id = 2,
            question = "Quantos livros compõem o Novo Testamento da Bíblia Sagrada?",
            options = listOf("39", "66", "27", "12"),
            correctIndex = 2,
            biblicalReference = "Cânon Bíblico",
            explanation = "A Bíblia contém 66 livros no total: 39 no Antigo Testamento e 27 no Novo Testamento.",
            difficulty = "Iniciante"
        ),
        QuizQuestion(
            id = 3,
            question = "Qual profeta foi engolido por um grande peixe após tentar fugir para Társis?",
            options = listOf("Jeremias", "Oseias", "Jonas", "Amós"),
            correctIndex = 2,
            biblicalReference = "Jonas 1:17",
            explanation = "Preparou, pois, o Senhor um grande peixe, para que tragasse a Jonas; e esteve Jonas três dias e três noites nas entranhas do peixe.",
            difficulty = "Iniciante"
        ),
        QuizQuestion(
            id = 4,
            question = "Em qual cidade nasceu o Senhor Jesus Cristo, cumprindo a profecia de Miqueias?",
            options = listOf("Nazaré", "Jerusalém", "Belém de Judá", "Cafarnaum"),
            correctIndex = 2,
            biblicalReference = "Miqueias 5:2 / Mateus 2:1",
            explanation = "E tu, Belém Efrata, de ti me sairá o que governará em Israel; e tendo nascido Jesus em Belém da Judeia...",
            difficulty = "Intermediário"
        ),
        QuizQuestion(
            id = 5,
            question = "Qual é o fruto do Espírito mencionado pelo apóstolo Paulo aos Gálatas?",
            options = listOf(
                "Riqueza, saúde e prosperidade",
                "Amor, gozo, paz, longanimidade, benignidade, bondade, fé, mansidão, temperança",
                "Sabedoria, justiça e oração",
                "Profecia, línguas e cura"
            ),
            correctIndex = 1,
            biblicalReference = "Gálatas 5:22-23",
            explanation = "O fruto do Espírito reflete o caráter de Cristo operado pelo Espírito Santo na vida do crente regenerado.",
            difficulty = "Intermediário"
        ),
        QuizQuestion(
            id = 6,
            question = "Qual destes discípulos andou sobre as águas em direção a Jesus antes de ter medo?",
            options = listOf("João", "Tiago", "Pedro", "André"),
            correctIndex = 2,
            biblicalReference = "Mateus 14:29",
            explanation = "E disse Pedro: Senhor, se és tu, manda-me ir ter contigo por cima das águas. E ele disse: Vem. E Pedro andou sobre as águas.",
            difficulty = "Iniciante"
        ),
        QuizQuestion(
            id = 7,
            question = "Quem sucedeu a Moisés como líder do povo de Israel na conquista de Canaã?",
            options = listOf("Josué", "Calebe", "Gideão", "Arão"),
            correctIndex = 0,
            biblicalReference = "Josué 1:1-2",
            explanation = "Moisés, meu servo, é morto; levanta-te pois agora, passa este Jordão, tu e todo este povo, à terra que eu lhes dou.",
            difficulty = "Intermediário"
        ),
        QuizQuestion(
            id = 8,
            question = "No livro de Atos, onde os discípulos foram pela primeira vez chamados de 'cristãos'?",
            options = listOf("Jerusalém", "Antioquia", "Roma", "Corinto"),
            correctIndex = 1,
            biblicalReference = "Atos 11:26",
            explanation = "E em Antioquia foram os discípulos, pela primeira vez, chamados cristãos.",
            difficulty = "Avançado"
        )
    )

    // Chave Pix Oficial da Igreja Batista Nova Aliança
    const val PIX_KEY = "pix@igrejabatistanovaalianca.com.br"
    const val PIX_NAME = "Igreja Batista Nova Aliança"
    const val PIX_CITY = "São Paulo - SP"
    const val PIX_CNPJ = "12.345.678/0001-90"
}
