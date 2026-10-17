package com.example.data.local

import com.example.data.local.entity.ReferenciaCruzada
import com.example.data.local.entity.Versiculo

/**
 * Dados pré-populados de domínio público para o banco SQLite Room:
 * - João Ferreira de Almeida Corrigida Fiel (ACF)
 * - João Ferreira de Almeida Atualizada (JFAA) - 1877
 * - João Ferreira de Almeida Corrigida (JFAC) - 1848
 * - Treasury of Scripture Knowledge (TSK) com referências cruzadas
 */
object BibliaSeedData {

    fun getInitialVersiculos(): List<Versiculo> {
        val list = mutableListOf<Versiculo>()

        // ----------------------------------------------------
        // SALMOS 23 (Livro 19, Cap 23) -> 19023001 a 19023006
        // ----------------------------------------------------
        // ACF
        list.add(Versiculo(19023001, "ACF", 19, 23, 1, "O Senhor é o meu pastor, nada me faltará."))
        list.add(Versiculo(19023002, "ACF", 19, 23, 2, "Deitar-me faz em verdes pastos, guia-me mansamente a águas tranqüilas."))
        list.add(Versiculo(19023003, "ACF", 19, 23, 3, "Refrigera a minha alma; guia-me pelas veredas da justiça, por amor do seu nome."))
        list.add(Versiculo(19023004, "ACF", 19, 23, 4, "Ainda que eu andasse pelo vale da sombra da morte, não temeria mal algum, porque tu estás comigo; a tua vara e o teu cajado me consolam."))
        list.add(Versiculo(19023005, "ACF", 19, 23, 5, "Preparas uma mesa perante mim na presença dos meus inimigos, unges a minha cabeça com óleo, o meu cálice transborda."))
        list.add(Versiculo(19023006, "ACF", 19, 23, 6, "Certamente que a bondade e a misericórdia me seguirão todos os dias da minha vida; e habitarei na casa do Senhor por longos dias."))

        // JFAA (1877 - Almeida Atualizada)
        list.add(Versiculo(19023001, "JFAA", 19, 23, 1, "O Senhor é o meu pastor; nada me faltará."))
        list.add(Versiculo(19023002, "JFAA", 19, 23, 2, "Ele me faz repousar em verdes pastos, guia-me mansamente a águas mansas."))
        list.add(Versiculo(19023003, "JFAA", 19, 23, 3, "Refrigera a minha alma; guia-me pelas veredas da retidão por amor do seu nome."))
        list.add(Versiculo(19023004, "JFAA", 19, 23, 4, "Ainda que eu ande pelo vale da sombra da morte, não temerei mal algum, porque tu estás comigo; a tua vara e o teu cajado me consolam."))
        list.add(Versiculo(19023005, "JFAA", 19, 23, 5, "Preparas uma mesa perante mim na presença dos meus adversários; unges com óleo a minha cabeça, o meu cálice transborda."))
        list.add(Versiculo(19023006, "JFAA", 19, 23, 6, "Certamente que a bondade e a misericórdia me seguirão todos os dias da minha vida, e habitarei na casa do Senhor por dias sem fim."))

        // JFAC (1848 - Almeida Corrigida Antiga)
        list.add(Versiculo(19023001, "JFAC", 19, 23, 1, "O Jehovah he meu pastor, nada me faltará."))
        list.add(Versiculo(19023002, "JFAC", 19, 23, 2, "Em prados de relva fresca me faz descançar; conduz-me mui mansamente ás aguas de repouso."))
        list.add(Versiculo(19023003, "JFAC", 19, 23, 3, "Restaura minha alma; encaminha-me pelas veredas da justiça, por amor do seu proprio nome."))
        list.add(Versiculo(19023004, "JFAC", 19, 23, 4, "Ainda que eu andasse pelo valle da sombra da morte, não temeria mal algum; porque tu estás commigo; o teu bordão e o teu cajado, elles me consolão."))
        list.add(Versiculo(19023005, "JFAC", 19, 23, 5, "Dispoens huma mesa perante mim á vista dos que me angustião; unges com oleo minha cabeça, meu copo transborda."))
        list.add(Versiculo(19023006, "JFAC", 19, 23, 6, "Por certo que a bondade e a beneficencia me hão de seguir todos os dias de minha vida: e habitarei na Casa de Jehovah por compridos dias."))

        // ----------------------------------------------------
        // JOÃO 3 (Livro 43, Cap 3) -> 43003014 a 43003018
        // ----------------------------------------------------
        // ACF
        list.add(Versiculo(43003014, "ACF", 43, 3, 14, "E, como Moisés levantou a serpente no deserto, assim importa que o Filho do homem seja levantado;"))
        list.add(Versiculo(43003015, "ACF", 43, 3, 15, "Para que todo aquele que nele crê não pereça, mas tenha a vida eterna."))
        list.add(Versiculo(43003016, "ACF", 43, 3, 16, "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna."))
        list.add(Versiculo(43003017, "ACF", 43, 3, 17, "Porque Deus enviou o seu Filho ao mundo, não para que condenasse o mundo, mas para que o mundo fosse salvo por ele."))
        list.add(Versiculo(43003018, "ACF", 43, 3, 18, "Quem crê nele não é condenado; mas quem não crê já está condenado, porquanto não crê no nome do unigênito Filho de Deus."))

        // JFAA (1877)
        list.add(Versiculo(43003014, "JFAA", 43, 3, 14, "E como Moisés levantou a serpente no deserto, assim importa que o Filho do homem seja levantado;"))
        list.add(Versiculo(43003015, "JFAA", 43, 3, 15, "para que todo aquele que nele crê tenha a vida eterna."))
        list.add(Versiculo(43003016, "JFAA", 43, 3, 16, "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna."))
        list.add(Versiculo(43003017, "JFAA", 43, 3, 17, "Porque Deus enviou o seu Filho ao mundo, não para que julgasse o mundo, mas para que o mundo fosse salvo por ele."))
        list.add(Versiculo(43003018, "JFAA", 43, 3, 18, "Quem crê nele não é julgado; mas o que não crê, já está julgado; porquanto não crê no nome do unigênito Filho de Deus."))

        // JFAC (1848)
        list.add(Versiculo(43003014, "JFAC", 43, 3, 14, "E como Moyses levantou a serpente no deserto, assim he necessario que o Filho do homem seja levantado:"))
        list.add(Versiculo(43003015, "JFAC", 43, 3, 15, "Para que todo aquelle que nelle crê, não pereça, mas tenha a vida eterna."))
        list.add(Versiculo(43003016, "JFAC", 43, 3, 16, "Porque de tal maneira amou Deos ao mundo, que deu a seu Filho unigenito, para que todo aquelle que nelle crê, não pereça, mas tenha a vida eterna."))
        list.add(Versiculo(43003017, "JFAC", 43, 3, 17, "Porque não enviou Deos a seu Filho ao mundo para que condemnasse o mundo, mas para que o mundo fosse salvo por elle."))
        list.add(Versiculo(43003018, "JFAC", 43, 3, 18, "Quem nelle crê, não he condemnado: mas o que não crê, já está condemnado, porquanto não crê no Nome do unigenito Filho de Deos."))

        // ----------------------------------------------------
        // JOÃO 1:1-5, 14 (Livro 43, Cap 1)
        // ----------------------------------------------------
        list.add(Versiculo(43001001, "ACF", 43, 1, 1, "No princípio era o Verbo, e o Verbo estava com Deus, e o Verbo era Deus."))
        list.add(Versiculo(43001002, "ACF", 43, 1, 2, "Ele estava no princípio com Deus."))
        list.add(Versiculo(43001003, "ACF", 43, 1, 3, "Todas as coisas foram feitas por ele, e sem ele nada do que foi feito se fez."))
        list.add(Versiculo(43001004, "ACF", 43, 1, 4, "Nele estava a vida, e a vida era a luz dos homens."))
        list.add(Versiculo(43001014, "ACF", 43, 1, 14, "E o Verbo se fez carne, e habitou entre nós, e vimos a sua glória, como a glória do unigênito do Pai, cheio de graça e de verdade."))

        list.add(Versiculo(43001001, "JFAA", 43, 1, 1, "No princípio era o Verbo, e o Verbo estava com Deus, e o Verbo era Deus."))
        list.add(Versiculo(43001002, "JFAA", 43, 1, 2, "Ele estava no princípio com Deus."))
        list.add(Versiculo(43001003, "JFAA", 43, 1, 3, "Todas as coisas foram feitas por intermédio dele, e sem ele nada do que foi feito se fez."))
        list.add(Versiculo(43001004, "JFAA", 43, 1, 4, "Nele estava a vida, e a vida era a luz dos homens."))
        list.add(Versiculo(43001014, "JFAA", 43, 1, 14, "E o Verbo se fez carne, e habitou entre nós, cheio de graça e de verdade; e vimos a sua glória, como a glória do unigênito do Pai."))

        list.add(Versiculo(43001001, "JFAC", 43, 1, 1, "No principio era a Palavra, e a Palavra estava com Deos, e a Palavra era Deos."))
        list.add(Versiculo(43001002, "JFAC", 43, 1, 2, "Esta estava no principio com Deos."))
        list.add(Versiculo(43001003, "JFAC", 43, 1, 3, "Todas as cousas forão feitas por ella; e sem ella nada do que foi feito se fez."))
        list.add(Versiculo(43001004, "JFAC", 43, 1, 4, "Nella estava a vida, e a vida era a luz dos homens."))
        list.add(Versiculo(43001014, "JFAC", 43, 1, 14, "E a Palavra se fez carne, e habitou entre nós, (e vimos sua gloria, como a gloria do Unigenito do Pai) cheia de graça e de verdade."))

        // ----------------------------------------------------
        // ROMANOS 5:8 & ROMANOS 8:28, 31, 38-39
        // ----------------------------------------------------
        list.add(Versiculo(45005008, "ACF", 45, 5, 8, "Mas Deus prova o seu amor para conosco, em que Cristo morreu por nós, sendo nós ainda pecadores."))
        list.add(Versiculo(45005008, "JFAA", 45, 5, 8, "Mas Deus dá prova do seu amor para conosco, em que, quando éramos ainda pecadores, Cristo morreu por nós."))
        list.add(Versiculo(45005008, "JFAC", 45, 5, 8, "Mas Deos recommenda seu amor para comnosco, em que, sendo nós ainda peccadores, Christo morreo por nós."))

        list.add(Versiculo(45008028, "ACF", 45, 8, 28, "E sabemos que todas as coisas concorrem para o bem daqueles que amam a Deus, daqueles que são chamados segundo o seu propósito."))
        list.add(Versiculo(45008031, "ACF", 45, 8, 31, "Que diremos, pois, a estas coisas? Se Deus é por nós, quem será contra nós?"))
        list.add(Versiculo(45008032, "ACF", 45, 8, 32, "Aquele que nem mesmo a seu próprio Filho poupou, antes o entregou por todos nós, como nos não dará também com ele todas as coisas?"))

        list.add(Versiculo(45008028, "JFAA", 45, 8, 28, "E sabemos que todas as coisas concorrem para o bem daqueles que amam a Deus, daqueles que são chamados segundo o seu propósito."))
        list.add(Versiculo(45008031, "JFAA", 45, 8, 31, "Que diremos, pois, a estas coisas? Se Deus é por nós, quem será contra nós?"))
        list.add(Versiculo(45008032, "JFAA", 45, 8, 32, "Aquele que nem mesmo a seu próprio Filho poupou, antes o entregou por todos nós, como não nos dará também com ele gratuitamente todas as coisas?"))

        list.add(Versiculo(45008028, "JFAC", 45, 8, 28, "E sabemos que todas as cousas cooperão juntamente para bem daquelles que amão a Deos, a saber, dos que são chamados segundo seu decreto."))
        list.add(Versiculo(45008031, "JFAC", 45, 8, 31, "Que diremos pois a estas cousas? Se Deos he por nós, quem será contra nós?"))
        list.add(Versiculo(45008032, "JFAC", 45, 8, 32, "Aquelle que nem a seu proprio Filho poupou, antes o entregou por todos nós, como não nos dará tambem com elle todas as cousas?"))

        // ----------------------------------------------------
        // FILIPENSES 4:6-7, 13, 19
        // ----------------------------------------------------
        list.add(Versiculo(50004006, "ACF", 50, 4, 6, "Não estejais inquietos por coisa alguma; antes as vossas petições sejam em tudo conhecidas diante de Deus pela oração e súplica, com ação de graças."))
        list.add(Versiculo(50004007, "ACF", 50, 4, 7, "E a paz de Deus, que excede todo o entendimento, guardará os vossos corações e os vossos sentimentos em Cristo Jesus."))
        list.add(Versiculo(50004013, "ACF", 50, 4, 13, "Posso todas as coisas em Cristo que me fortalece."))
        list.add(Versiculo(50004019, "ACF", 50, 4, 19, "O meu Deus, segundo as suas riquezas, suprirá todas as vossas necessidades em glória, por Cristo Jesus."))

        list.add(Versiculo(50004006, "JFAA", 50, 4, 6, "Não andeis ansiosos por coisa alguma; antes em tudo sejam os vossos pedidos conhecidos diante de Deus pela oração e súplica com ações de graças."))
        list.add(Versiculo(50004007, "JFAA", 50, 4, 7, "E a paz de Deus, que excede todo o entendimento, guardará os vossos corações e os vossos pensamentos em Cristo Jesus."))
        list.add(Versiculo(50004013, "JFAA", 50, 4, 13, "Posso todas as coisas naquele que me fortalece."))
        list.add(Versiculo(50004019, "JFAA", 50, 4, 19, "Meu Deus suprirá todas as vossas necessidades segundo as suas riquezas na glória em Cristo Jesus."))

        list.add(Versiculo(50004006, "JFAC", 50, 4, 6, "Não estejais sollicitos de cousa alguma; antes em tudo sejão vossas petições conhecidas diante de Deos, por oração e rogos com acções de graças."))
        list.add(Versiculo(50004007, "JFAC", 50, 4, 7, "E a paz de Deos, que sobrepuja todo o entendimento, guardará vossos corações e vossos sentimentos em Christo Jesus."))
        list.add(Versiculo(50004013, "JFAC", 50, 4, 13, "Tudo posso em Christo que me conforta."))
        list.add(Versiculo(50004019, "JFAC", 50, 4, 19, "E meu Deos supprirá todas vossas necessidades, segundo suas riquezas em gloria, em Christo Jesus."))

        // ----------------------------------------------------
        // 1 JOÃO 4:9-10
        // ----------------------------------------------------
        list.add(Versiculo(62004009, "ACF", 62, 4, 9, "Nisto se manifesta o amor de Deus para conosco: que Deus enviou seu Filho unigênito ao mundo, para que por ele vivamos."))
        list.add(Versiculo(62004010, "ACF", 62, 4, 10, "Nisto está o amor, não em que nós tenhamos amado a Deus, mas em que ele nos amou a nós, e enviou seu Filho para propiciação pelos nossos pecados."))

        list.add(Versiculo(62004009, "JFAA", 62, 4, 9, "Nisto se manifestou o amor de Deus para conosco: em que Deus enviou seu Filho unigênito ao mundo, para que por meio dele vivamos."))
        list.add(Versiculo(62004010, "JFAA", 62, 4, 10, "Nisto está o amor: não em que nós tenhamos amado a Deus, mas em que ele nos amou a nós, e enviou seu Filho como propiciação pelos nossos pecados."))

        list.add(Versiculo(62004009, "JFAC", 62, 4, 9, "Nisto se manifestou a caridade de Deos para comnosco, em que Deos enviou a seu Filho unigenito ao mundo, para que por elle vivamos."))
        list.add(Versiculo(62004010, "JFAC", 62, 4, 10, "Nisto está a caridade, não em que nós tenhamos amado a Deos, mas em que elle nos amou a nós, e enviou a seu Filho em propiciação por nossos peccados."))

        // ----------------------------------------------------
        // GÊNESIS 1:1-3, ISAÍAS 9:6, JOÃO 10:11
        // ----------------------------------------------------
        list.add(Versiculo(1001001, "ACF", 1, 1, 1, "No princípio criou Deus os céus e a terra."))
        list.add(Versiculo(1001002, "ACF", 1, 1, 2, "E a terra era sem forma e vazia; e havia trevas sobre a face do abismo; e o Espírito de Deus se movia sobre a face das águas."))
        list.add(Versiculo(1001003, "ACF", 1, 1, 3, "E disse Deus: Haja luz; e houve luz."))

        list.add(Versiculo(1001001, "JFAA", 1, 1, 1, "No princípio criou Deus os céus e a terra."))
        list.add(Versiculo(1001002, "JFAA", 1, 1, 2, "A terra, porém, era sem forma e vazia; havia trevas sobre a face do abismo, e o Espírito de Deus pairava por sobre as águas."))
        list.add(Versiculo(1001003, "JFAA", 1, 1, 3, "Disse Deus: haja luz. E houve luz."))

        list.add(Versiculo(1001001, "JFAC", 1, 1, 1, "No principio criou Deos os ceos e a terra."))
        list.add(Versiculo(1001002, "JFAC", 1, 1, 2, "E a terra era informe e vasia; e trevas havião sobre a face do abysmo, e o Espirito de Deos se movia sobre a face das aguas."))
        list.add(Versiculo(1001003, "JFAC", 1, 1, 3, "E disse Deos: Haja luz. E houve luz."))

        list.add(Versiculo(23009006, "ACF", 23, 9, 6, "Porque um menino nos nasceu, um filho se nos deu, e o principado está sobre os seus ombros, e se chamará o seu nome: Maravilhoso, Conselheiro, Deus Forte, Pai da Eternidade, Príncipe da Paz."))
        list.add(Versiculo(23009006, "JFAA", 23, 9, 6, "Porque um menino nos nasceu, um filho se nos deu; e o governo estará sobre os seus ombros; e o seu nome será: Maravilhoso Conselheiro, Deus Forte, Pai Eterno, Príncipe da Paz."))
        list.add(Versiculo(23009006, "JFAC", 23, 9, 6, "Porque hum Menino nos he nascido, hum Filho se nos deu, e o principado estará sobre seu hombro; e chamar-se-ha seu nome: Maravilhoso, Conselheiro, Deos forte, Pai da eternidade, Principe da paz."))

        list.add(Versiculo(43010011, "ACF", 43, 10, 11, "Eu sou o bom pastor; o bom pastor dá a sua vida pelas ovelhas."))
        list.add(Versiculo(43010011, "JFAA", 43, 10, 11, "Eu sou o bom pastor; o bom pastor dá a sua vida pelas ovelhas."))
        list.add(Versiculo(43010011, "JFAC", 43, 10, 11, "Eu sou o bom Pastor: o bom Pastor dá sua vida por suas ovelhas."))

        // ----------------------------------------------------
        // SALMOS 1 (Livro 19, Cap 1) -> 19001001 a 19001006
        // ----------------------------------------------------
        list.add(Versiculo(19001001, "ACF", 19, 1, 1, "Bem-aventurado o homem que não anda segundo o conselho dos ímpios, nem se detém no caminho dos pecadores, nem se assenta na roda dos escarnecedores."))
        list.add(Versiculo(19001002, "ACF", 19, 1, 2, "Antes tem o seu prazer na lei do Senhor, e na sua lei medita de dia e de noite."))
        list.add(Versiculo(19001003, "ACF", 19, 1, 3, "Pois será como a árvore plantada junto a ribeiros de águas, a qual dá o seu fruto no seu tempo; as suas folhas não cairão, e tudo quanto fizer prosperará."))
        list.add(Versiculo(19001004, "ACF", 19, 1, 4, "Não são assim os ímpios; mas são como a moinha que o vento espalha."))
        list.add(Versiculo(19001005, "ACF", 19, 1, 5, "Por isso os ímpios não subsistirão no juízo, nem os pecadores na congregação dos justos."))
        list.add(Versiculo(19001006, "ACF", 19, 1, 6, "Porque o Senhor conhece o caminho dos justos; porém o caminho dos ímpios perecerá."))

        list.add(Versiculo(19001001, "JFAA", 19, 1, 1, "Bem-aventurado o varão que não anda segundo o conselho dos ímpios, nem se detém no caminho dos pecadores, nem se assenta na roda dos escarnecedores."))
        list.add(Versiculo(19001002, "JFAA", 19, 1, 2, "Antes tem o seu prazer na lei do Senhor, e na sua lei medita de dia e de noite."))
        list.add(Versiculo(19001003, "JFAA", 19, 1, 3, "Ele é como árvore plantada junto a corrente de águas, que dá o seu fruto na estação própria, e cuja folha não cai; e tudo quanto fizer prosperará."))
        list.add(Versiculo(19001004, "JFAA", 19, 1, 4, "Não são assim os ímpios; mas são como a palha que o vento dispersa."))
        list.add(Versiculo(19001005, "JFAA", 19, 1, 5, "Pelo que os ímpios não subsistirão no juízo, nem os pecadores na congregação dos justos."))
        list.add(Versiculo(19001006, "JFAA", 19, 1, 6, "Pois o Senhor conhece o caminho dos justos, mas o caminho dos ímpios perecerá."))

        list.add(Versiculo(19001001, "JFAC", 19, 1, 1, "Bem-aventurado o varão que não anda no conselho dos impios, nem se detem no caminho dos peccadores, nem se assenta na cadeira dos zombadores."))
        list.add(Versiculo(19001002, "JFAC", 19, 1, 2, "Antes seu affecto está na Lei de Jehovah, e em sua Lei medita de dia e de noite."))
        list.add(Versiculo(19001003, "JFAC", 19, 1, 3, "Porque será como a arvore plantada junto ás correntes das aguas, a qual dá seu fructo em sua estação, e cuja folha não cahe; e tudo quanto fizer prosperará."))
        list.add(Versiculo(19001004, "JFAC", 19, 1, 4, "Não são assim os impios; mas são como o moinho que o vento espalha e dissipa."))
        list.add(Versiculo(19001005, "JFAC", 19, 1, 5, "Por tanto os impios não subsistirão no juizo, nem os peccadores na congregação dos justos."))
        list.add(Versiculo(19001006, "JFAC", 19, 1, 6, "Porque o Senhor conhece o caminho dos justos; mas o caminho dos impios perecerá."))

        // ----------------------------------------------------
        // SALMOS 91 (Livro 19, Cap 91)
        // ----------------------------------------------------
        list.add(Versiculo(19091001, "ACF", 19, 91, 1, "Aquele que habita no esconderijo do Altíssimo, à sombra do Onipotente descansará."))
        list.add(Versiculo(19091002, "ACF", 19, 91, 2, "Direi do Senhor: Ele é o meu Deus, o meu refúgio, a minha fortaleza, e nele confiarei."))
        list.add(Versiculo(19091003, "ACF", 19, 91, 3, "Porque ele te livrará do laço do passarinheiro, e da peste perniciosa."))
        list.add(Versiculo(19091004, "ACF", 19, 91, 4, "Ele te cobrirá com as suas penas, e debaixo das suas asas te confiarás; a sua verdade será o teu escudo e broquel."))
        list.add(Versiculo(19091005, "ACF", 19, 91, 5, "Não terás medo do terror de noite nem da seta que voa de dia."))
        list.add(Versiculo(19091006, "ACF", 19, 91, 6, "Nem da peste que anda na escuridão, nem da mortandade que assola ao meio-dia."))
        list.add(Versiculo(19091007, "ACF", 19, 91, 7, "Mil cairão ao teu lado, e dez mil à tua direita, mas não chegará a ti."))

        list.add(Versiculo(19091001, "JFAA", 19, 91, 1, "Aquele que habita no esconderijo do Altíssimo, à sombra do Onipotente descansará."))
        list.add(Versiculo(19091002, "JFAA", 19, 91, 2, "Direi do Senhor: Ele é o meu refúgio e a minha fortaleza, o meu Deus, em quem confio."))
        list.add(Versiculo(19091003, "JFAA", 19, 91, 3, "Porque Ele te livra do laço do passarinheiro, e da peste perniciosa."))
        list.add(Versiculo(19091004, "JFAA", 19, 91, 4, "Ele te cobre com as Suas penas, e debaixo das Suas asas encontras refúgio; a Sua fidelidade é escudo e broquel."))
        list.add(Versiculo(19091005, "JFAA", 19, 91, 5, "Não temerás os terrores da noite, nem a seta que voa de dia."))
        list.add(Versiculo(19091006, "JFAA", 19, 91, 6, "Nem a peste que anda nas trevas, nem a mortandade que assola ao meio-dia."))
        list.add(Versiculo(19091007, "JFAA", 19, 91, 7, "Cairão mil ao teu lado, e dez mil à tua direita; mas tu não serás atingido."))

        list.add(Versiculo(19091001, "JFAC", 19, 91, 1, "Aquelle que habita no retiro do Altissimo, descansará seguro á sombra do Omnipotente."))
        list.add(Versiculo(19091002, "JFAC", 19, 91, 2, "Direi do Senhor: Elle he meu refugio e minha fortaleza, meu Deos, em quem esperarei."))
        list.add(Versiculo(19091003, "JFAC", 19, 91, 3, "Porque elle te livrará do laço do caçador, e da peste destruidora."))
        list.add(Versiculo(19091004, "JFAC", 19, 91, 4, "Com suas pennas te cobrirá, e debaixo de suas azas te acolherás; sua verdade he escudo e pavêz."))
        list.add(Versiculo(19091005, "JFAC", 19, 91, 5, "Não terás medo dos pavores nocturnos, nem da setta que voa de dia."))
        list.add(Versiculo(19091006, "JFAC", 19, 91, 6, "Nem da peste que anda na escuridade, nem do contagio que assola ao meio dia."))
        list.add(Versiculo(19091007, "JFAC", 19, 91, 7, "Cahirão mil ao teu lado, e dez mil á tua direita; mas a ti nada chegará."))

        return list
    }

    /**
     * Referências Cruzadas do Treasury of Scripture Knowledge (TSK - Domínio Público)
     * interligando os versículos principais por códigos BBCCCVVV.
     */
    fun getInitialReferenciasCruzadas(): List<ReferenciaCruzada> {
        return listOf(
            // João 3:16 (43003016)
            ReferenciaCruzada(43003016, 45005008, "Amor divino manifesto na entrega de Cristo"),
            ReferenciaCruzada(43003016, 62004009, "O envio do Filho unigênito ao mundo"),
            ReferenciaCruzada(43003016, 62004010, "Propiciação pelos pecados da humanidade"),
            ReferenciaCruzada(43003016, 45008032, "Deus não poupou o Seu próprio Filho"),
            ReferenciaCruzada(43003016, 23009006, "Profecia messiânica do Menino nos nascido"),
            ReferenciaCruzada(43003016, 43001014, "O Verbo feito carne habitando entre os homens"),

            // Salmos 23:1 (19023001)
            ReferenciaCruzada(19023001, 43010011, "Jesus se revela como o Bom Pastor"),
            ReferenciaCruzada(19023001, 50004019, "O Senhor supre todas as nossas necessidades"),
            ReferenciaCruzada(19023001, 50004006, "Descanso e paz perante a ansiedade"),

            // Salmos 23:4 (19023004)
            ReferenciaCruzada(19023004, 45008031, "Se Deus é por nós, quem será contra nós?"),
            ReferenciaCruzada(19023004, 50004007, "A paz de Deus que guarda os corações"),

            // Filipenses 4:13 (50004013)
            ReferenciaCruzada(50004013, 45008028, "Fortalecidos naquele que cumpre o propósito"),
            ReferenciaCruzada(50004013, 45008031, "Mais do que vencedores com Cristo"),

            // João 1:1 (43001001)
            ReferenciaCruzada(43001001, 1001001, "No princípio Deus criou os céus e a terra"),
            ReferenciaCruzada(43001001, 43001014, "O Verbo eterno que se fez carne"),
            ReferenciaCruzada(43001001, 23009006, "O Deus forte e Pai da eternidade")
        )
    }
}
