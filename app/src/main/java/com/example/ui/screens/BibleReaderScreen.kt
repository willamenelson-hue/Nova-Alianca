package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BbcccvvvHelper
import com.example.data.local.entity.Anotacao
import com.example.data.local.entity.Marcacao
import com.example.data.local.entity.Versiculo
import com.example.data.model.BibleBook
import com.example.data.model.BibleVersion
import com.example.data.model.ReferenciaComTexto
import com.example.data.model.Testament
import com.example.service.VoiceGender
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppViewModel
import com.example.ui.viewmodel.ReaderColorMode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleReaderScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val bibleState by viewModel.bibleState.collectAsState()
    val ttsState by viewModel.ttsState.collectAsState()

    // Dados do Room SQLite
    val versiculos by viewModel.versiculosDoCapitulo.collectAsState()
    val marcacoesMap by viewModel.marcacoesDoCapitulo.collectAsState()
    val referenciasIds by viewModel.referenciasIdsNoCapitulo.collectAsState()

    // Modais e Diálogos de Ação
    var selectedVersiculoForMenu by remember { mutableStateOf<Versiculo?>(null) }
    var versiculoForCrossReferences by remember { mutableStateOf<Versiculo?>(null) }
    var versiculoForNoteDialog by remember { mutableStateOf<Versiculo?>(null) }
    var showColorPaletteSheet by remember { mutableStateOf<Versiculo?>(null) }
    var showVersionSelectorSheet by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }

    val currentBook = viewModel.bibleRepository.getBookById(bibleState.currentBookId)

    // Cores de fundo e texto de acordo com o modo de leitura
    val (readerBg, readerTextColor) = when (bibleState.readerTheme) {
        ReaderColorMode.DEFAULT -> MaterialTheme.colorScheme.background to MaterialTheme.colorScheme.onBackground
        ReaderColorMode.PARCHMENT -> ParchmentLight to ParchmentText
        ReaderColorMode.DARK -> DarkReaderBg to Color(0xFFE2E2E2)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(readerBg)
            .testTag("bible_reader_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Barra Superior de Controle do Módulo Bíblico
            Surface(
                color = RoyalBluePrimary,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Seletor de Livro e Capítulo
                        OutlinedButton(
                            onClick = { viewModel.toggleBookSelector(true) },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(WarmGoldPrimary)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("book_selector_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = WarmGoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${currentBook?.name ?: "Livro"} ${bibleState.currentChapter} ▾",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Seletor de Versão (ACF / JFAA / JFAC)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilledTonalButton(
                                onClick = { showVersionSelectorSheet = true },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = WarmGoldPrimary,
                                    contentColor = DeepNavy
                                ),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("version_selector_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = bibleState.selectedVersion.code,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // Ajustes de Leitura (Fonte e Tema)
                            IconButton(
                                onClick = { showSettingsSheet = true },
                                modifier = Modifier.testTag("reader_settings_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatSize,
                                    contentDescription = "Tamanho e Tema",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    // Campo de Busca Rápida de Versículos
                    OutlinedTextField(
                        value = bibleState.searchQuery,
                        onValueChange = { query -> viewModel.performBibleSearch(query) },
                        placeholder = { Text("Pesquisar palavra ou versículo...", fontSize = 13.sp, color = Color.LightGray) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = WarmGoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            if (bibleState.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.closeSearch() }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Limpar", tint = Color.White)
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = WarmGoldPrimary,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("bible_search_input")
                    )
                }
            }

            // Barra de Controle do Leitor TTS Google
            TtsControlBar(
                ttsState = ttsState,
                onPlayPause = { viewModel.togglePlayPauseTts() },
                onStop = { viewModel.stopTts() },
                onGenderChange = { gender -> viewModel.setVoiceGender(gender) },
                onRateChange = { rate -> viewModel.setSpeechRate(rate) },
                modifier = Modifier.fillMaxWidth()
            )

            // Lista de Versículos do Capítulo com Suporte a Marcações e Referências TSK
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = "${currentBook?.name ?: ""} ${bibleState.currentChapter}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = RoyalBluePrimary
                            )
                        )
                        Text(
                            text = "${bibleState.selectedVersion.title} (${bibleState.selectedVersion.year})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = WarmGoldDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                items(versiculos) { versiculo ->
                    val isTtsActive = ttsState.currentVerseNumber == versiculo.numero && ttsState.isSpeaking
                    val marcacoes = marcacoesMap[versiculo.id] ?: emptyList()
                    val hasCrossReferences = referenciasIds.contains(versiculo.id)

                    VersiculoItemView(
                        versiculo = versiculo,
                        fontSizeSp = bibleState.fontSizeSp,
                        textColor = readerTextColor,
                        isTtsActive = isTtsActive,
                        marcacoes = marcacoes,
                        hasCrossReferences = hasCrossReferences,
                        onClick = { selectedVersiculoForMenu = versiculo },
                        onLongClick = { selectedVersiculoForMenu = versiculo },
                        onCrossReferenceClick = { versiculoForCrossReferences = versiculo }
                    )
                }

                // Navegação entre Capítulos
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.previousChapter() },
                            enabled = bibleState.currentChapter > 1 || bibleState.currentBookId > 1
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cap. Anterior")
                        }

                        FilledTonalButton(
                            onClick = { viewModel.nextChapter() },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = RoyalBluePrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Próximo Cap.")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
        }

        // =================================================================
        // Menu Flutuante ao Tocar/Segurar no Versículo (Bottom Sheet)
        // Opções: 'Destacar cor', 'Sublinhar', 'Anotar pensamento' e 'Ver referências cruzadas'
        // =================================================================
        if (selectedVersiculoForMenu != null) {
            val v = selectedVersiculoForMenu!!
            VerseFloatingMenuBottomSheet(
                versiculo = v,
                onDismiss = { selectedVersiculoForMenu = null },
                onDestacarCor = {
                    showColorPaletteSheet = v
                    selectedVersiculoForMenu = null
                },
                onSublinhar = {
                    viewModel.sublinharVersiculo(v.id, "#D4AF37")
                    selectedVersiculoForMenu = null
                },
                onAnotar = {
                    versiculoForNoteDialog = v
                    selectedVersiculoForMenu = null
                },
                onVerReferencias = {
                    versiculoForCrossReferences = v
                    selectedVersiculoForMenu = null
                },
                onOuvirTts = {
                    viewModel.readSpecificVerse(v)
                    selectedVersiculoForMenu = null
                },
                onCopiar = {
                    val ref = BbcccvvvHelper.formatReference(v.id)
                    clipboardManager.setText(AnnotatedString("\"${v.texto}\" — $ref (${v.versao})"))
                    viewModel.showFeedback("Versículo copiado!")
                    selectedVersiculoForMenu = null
                },
                onLimparMarcacoes = {
                    viewModel.limparTodasMarcacoes(v.id)
                    selectedVersiculoForMenu = null
                }
            )
        }

        // Paleta de Cores para Marca-Texto
        if (showColorPaletteSheet != null) {
            val v = showColorPaletteSheet!!
            ColorPaletteBottomSheet(
                versiculo = v,
                onDismiss = { showColorPaletteSheet = null },
                onSelectColor = { corHex ->
                    viewModel.destacarCor(v.id, corHex)
                    showColorPaletteSheet = null
                },
                onRemoverMarcatexto = {
                    viewModel.removerMarcacao(v.id, "marcatexto")
                    showColorPaletteSheet = null
                }
            )
        }

        // Diálogo / Bottom Sheet de Referências Cruzadas TSK
        if (versiculoForCrossReferences != null) {
            val v = versiculoForCrossReferences!!
            CrossReferencesBottomSheet(
                versiculo = v,
                viewModel = viewModel,
                onDismiss = { versiculoForCrossReferences = null },
                onNavigateToVerse = { targetId ->
                    val bookId = BbcccvvvHelper.getBookId(targetId)
                    val chapter = BbcccvvvHelper.getChapter(targetId)
                    viewModel.selectBookAndChapter(bookId, chapter)
                    versiculoForCrossReferences = null
                }
            )
        }

        // Diálogo para Anotar Pensamento / Reflexão do Versículo
        if (versiculoForNoteDialog != null) {
            val v = versiculoForNoteDialog!!
            VerseNoteDialog(
                versiculo = v,
                viewModel = viewModel,
                onDismiss = { versiculoForNoteDialog = null }
            )
        }

        // Seletor de Versão (ACF / JFAA / JFAC)
        if (showVersionSelectorSheet) {
            VersionSelectorBottomSheet(
                currentVersion = bibleState.selectedVersion,
                onSelectVersion = { version ->
                    viewModel.changeVersion(version)
                    showVersionSelectorSheet = false
                },
                onDismiss = { showVersionSelectorSheet = false }
            )
        }

        // Seletor de Livros e Capítulos
        if (bibleState.isBookSelectorOpen) {
            BookAndChapterSelectorDialog(
                allBooks = viewModel.bibleRepository.getAllBooks(),
                currentBookId = bibleState.currentBookId,
                currentChapter = bibleState.currentChapter,
                onSelect = { bookId, chapter ->
                    viewModel.selectBookAndChapter(bookId, chapter)
                },
                onDismiss = { viewModel.toggleBookSelector(false) }
            )
        }

        // Modal de Configurações de Leitura (Fonte e Tema)
        if (showSettingsSheet) {
            ReaderSettingsBottomSheet(
                currentFontSize = bibleState.fontSizeSp,
                currentTheme = bibleState.readerTheme,
                onFontSizeChange = { viewModel.setFontSize(it) },
                onThemeChange = { viewModel.setReaderTheme(it) },
                onDismiss = { showSettingsSheet = false }
            )
        }
    }
}

/**
 * Exibe o versículo individual com estilização visual de marca-texto (background color)
 * e sublinhado (TextDecoration.Underline) sincronizados com o Room SQLite.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VersiculoItemView(
    versiculo: Versiculo,
    fontSizeSp: Int,
    textColor: Color,
    isTtsActive: Boolean,
    marcacoes: List<Marcacao>,
    hasCrossReferences: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onCrossReferenceClick: () -> Unit
) {
    val marcatexto = marcacoes.firstOrNull { it.tipo == "marcatexto" }
    val sublinhado = marcacoes.firstOrNull { it.tipo == "sublinhado" }

    // Cor de fundo do marca-texto
    val backgroundColor = when {
        isTtsActive -> WarmGoldPrimary.copy(alpha = 0.4f)
        marcatexto != null -> parseHexColor(marcatexto.corHex).copy(alpha = 0.45f)
        else -> Color.Transparent
    }

    // Sublinhado no texto
    val textDecoration = if (sublinhado != null) TextDecoration.Underline else TextDecoration.None

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(vertical = 5.dp, horizontal = 6.dp)
            .testTag("versiculo_item_${versiculo.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Número do versículo
            Text(
                text = "${versiculo.numero} ",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = WarmGoldDark,
                    fontSize = (fontSizeSp - 2).sp
                ),
                modifier = Modifier.padding(top = 1.dp)
            )

            // Texto do versículo com estilização
            Text(
                text = versiculo.texto,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = fontSizeSp.sp,
                    lineHeight = (fontSizeSp * 1.48).sp,
                    color = textColor,
                    textDecoration = textDecoration
                ),
                modifier = Modifier.weight(1f)
            )

            // Ícone indicador de Referências Cruzadas TSK
            if (hasCrossReferences) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = RoyalBluePrimary.copy(alpha = 0.12f),
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .clickable(onClick = onCrossReferenceClick)
                        .testTag("cross_ref_indicator_${versiculo.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "Referências Cruzadas",
                            tint = WarmGoldDark,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "TSK",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBluePrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Menu flutuante (Bottom Sheet) acionado ao tocar ou segurar no versículo
 * com as 4 opções obrigatórias:
 * 1. Destacar cor
 * 2. Sublinhar
 * 3. Anotar pensamento
 * 4. Ver referências cruzadas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerseFloatingMenuBottomSheet(
    versiculo: Versiculo,
    onDismiss: () -> Unit,
    onDestacarCor: () -> Unit,
    onSublinhar: () -> Unit,
    onAnotar: () -> Unit,
    onVerReferencias: () -> Unit,
    onOuvirTts: () -> Unit,
    onCopiar: () -> Unit,
    onLimparMarcacoes: () -> Unit
) {
    val refFormatada = BbcccvvvHelper.formatReference(versiculo.id)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = refFormatada,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "ID BBCCCVVV: ${versiculo.id} • Versão ${versiculo.versao}",
                        style = MaterialTheme.typography.labelSmall,
                        color = WarmGoldDark
                    )
                }
                IconButton(onClick = onLimparMarcacoes) {
                    Icon(
                        imageVector = Icons.Default.FormatClear,
                        contentDescription = "Limpar marcações",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "“${versiculo.texto}”",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // As 4 Opções Principais
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                // 1. Destacar cor (marcatexto)
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.BorderColor, contentDescription = null, tint = WarmGoldDark) },
                    label = { Text("Destacar cor (Marca-texto)", fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = onDestacarCor,
                    modifier = Modifier.testTag("action_destacar_cor")
                )

                // 2. Sublinhar (TextDecoration.Underline)
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.FormatUnderlined, contentDescription = null, tint = RoyalBluePrimary) },
                    label = { Text("Sublinhar versículo", fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = onSublinhar,
                    modifier = Modifier.testTag("action_sublinhar")
                )

                // 3. Anotar pensamento
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.EditNote, contentDescription = null, tint = RoyalBluePrimary) },
                    label = { Text("Anotar pensamento / reflexão", fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = onAnotar,
                    modifier = Modifier.testTag("action_anotar")
                )

                // 4. Ver referências cruzadas
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Link, contentDescription = null, tint = WarmGoldDark) },
                    label = { Text("Ver referências cruzadas (TSK)", fontWeight = FontWeight.SemiBold) },
                    selected = false,
                    onClick = onVerReferencias,
                    modifier = Modifier.testTag("action_referencias_cruzadas")
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // Ações complementares (Áudio TTS e Copiar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onOuvirTts) {
                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ouvir TTS")
                }
                TextButton(onClick = onCopiar) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Copiar")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

/**
 * Bottom Sheet com Paleta de Cores para Marca-Texto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColorPaletteBottomSheet(
    versiculo: Versiculo,
    onDismiss: () -> Unit,
    onSelectColor: (String) -> Unit,
    onRemoverMarcatexto: () -> Unit
) {
    val cores = listOf(
        "#FFF176" to "Amarelo Luz",
        "#A5D6A7" to "Verde Esperança",
        "#90CAF9" to "Azul Céu",
        "#F48FB1" to "Rosa Graça",
        "#FFCC80" to "Laranja Fogo",
        "#CE93D8" to "Lavanda Real"
    )

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Escolha a cor do Marca-Texto",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBluePrimary
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                cores.forEach { (hex, nome) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onSelectColor(hex) }
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(parseHexColor(hex))
                                .border(1.5.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = nome.split(" ").first(), fontSize = 10.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onRemoverMarcatexto,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Remover Marca-Texto")
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/**
 * Diálogo / Bottom Sheet listando as referências cruzadas TSK do versículo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CrossReferencesBottomSheet(
    versiculo: Versiculo,
    viewModel: AppViewModel,
    onDismiss: () -> Unit,
    onNavigateToVerse: (Int) -> Unit
) {
    val referenciasComTexto by viewModel.getReferenciasCruzadasComTexto(versiculo.id).collectAsState(initial = emptyList())
    val refOrigemFormatada = BbcccvvvHelper.formatReference(versiculo.id)

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = null,
                    tint = WarmGoldDark
                )
                Text(
                    text = "Referências Cruzadas (TSK)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            }
            Text(
                text = "Versículo base: $refOrigemFormatada (${versiculo.versao})",
                style = MaterialTheme.typography.bodySmall,
                color = WarmGoldDark,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (referenciasComTexto.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhuma referência cruzada direta mapeada para este versículo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(referenciasComTexto) { ref ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToVerse(ref.destinoVersiculoId) },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = ref.referenciaFormatada,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = RoyalBluePrimary
                                    )
                                    Text(
                                        text = "Ir para >",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = WarmGoldDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                if (!ref.contextoDescricao.isNullOrBlank()) {
                                    Text(
                                        text = ref.contextoDescricao,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = WarmGoldDark
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "“${ref.textoDestino}”",
                                    style = MaterialTheme.typography.bodySmall,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

/**
 * Diálogo para Salvar e Listar Anotações do Versículo
 */
@Composable
private fun VerseNoteDialog(
    versiculo: Versiculo,
    viewModel: AppViewModel,
    onDismiss: () -> Unit
) {
    val anotacoes by viewModel.getAnotacoesDoVersiculo(versiculo.id).collectAsState(initial = emptyList())
    var noteInput by remember { mutableStateOf("") }
    val refFormatada = BbcccvvvHelper.formatReference(versiculo.id)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Anotar Pensamento",
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
                Text(
                    text = "$refFormatada (${versiculo.versao})",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmGoldDark
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "“${versiculo.texto}”",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = noteInput,
                    onValueChange = { noteInput = it },
                    label = { Text("Sua reflexão ou nota...") },
                    placeholder = { Text("Escreva uma reflexão sobre esta passagem...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                if (anotacoes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Anotações anteriores:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyColumn(modifier = Modifier.heightIn(max = 120.dp)) {
                        items(anotacoes) { note ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = note.textoAnotacao,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (noteInput.isNotBlank()) {
                        viewModel.salvarAnotacao(versiculo.id, noteInput)
                        onDismiss()
                    }
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}

/**
 * Seletor de Versão (ACF, JFAA 1877, JFAC 1848)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VersionSelectorBottomSheet(
    currentVersion: BibleVersion,
    onSelectVersion: (BibleVersion) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Escolha a Versão da Bíblia",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBluePrimary
            )
            Text(
                text = "Todas as versões armazenadas localmente no SQLite offline:",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            BibleVersion.values().forEach { version ->
                val isSelected = currentVersion == version
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectVersion(version) },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) WarmGoldContainer else MaterialTheme.colorScheme.surface
                    ),
                    border = if (isSelected) ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(WarmGoldDark)
                    ) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onSelectVersion(version) }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${version.code} - ${version.title}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isSelected) RoyalBluePrimary else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${version.subtitle} (${version.year})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
private fun TtsControlBar(
    ttsState: com.example.service.TtsState,
    onPlayPause: () -> Unit,
    onStop: () -> Unit,
    onGenderChange: (VoiceGender) -> Unit,
    onRateChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = DeepNavy,
        modifier = modifier.testTag("tts_control_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onPlayPause) {
                    Icon(
                        imageVector = if (ttsState.isSpeaking) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                        contentDescription = "Reproduzir Áudio",
                        tint = WarmGoldPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
                if (ttsState.isSpeaking) {
                    IconButton(onClick = onStop) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "Parar",
                            tint = Color.White
                        )
                    }
                }
                Text(
                    text = if (ttsState.isSpeaking) "Lendo v.${ttsState.currentVerseNumber}" else "Áudio TTS Google",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                FilterChip(
                    selected = ttsState.selectedGender == VoiceGender.FEMALE,
                    onClick = { onGenderChange(VoiceGender.FEMALE) },
                    label = { Text("Voz Fem.", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = WarmGoldPrimary,
                        selectedLabelColor = DeepNavy
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )

                FilterChip(
                    selected = ttsState.selectedGender == VoiceGender.MALE,
                    onClick = { onGenderChange(VoiceGender.MALE) },
                    label = { Text("Voz Masc.", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = WarmGoldPrimary,
                        selectedLabelColor = DeepNavy
                    )
                )
            }
        }
    }
}

@Composable
private fun BookAndChapterSelectorDialog(
    allBooks: List<BibleBook>,
    currentBookId: Int,
    currentChapter: Int,
    onSelect: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedBook by remember { mutableStateOf(allBooks.firstOrNull { it.id == currentBookId } ?: allBooks.first()) }
    var selectedTab by remember { mutableStateOf(if (selectedBook.testament == Testament.OLD) 0 else 1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Selecionar Passagem", fontWeight = FontWeight.Bold)
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Antigo Testamento") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Novo Testamento") }
                    )
                }
            }
        },
        text = {
            val filteredBooks = allBooks.filter {
                if (selectedTab == 0) it.testament == Testament.OLD else it.testament == Testament.NEW
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Livro: ${selectedBook.name}",
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredBooks) { book ->
                        FilterChip(
                            selected = book.id == selectedBook.id,
                            onClick = { selectedBook = book },
                            label = { Text(book.name, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = RoyalBluePrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Escolha o Capítulo:", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(6.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items((1..selectedBook.totalChapters).toList()) { capNum ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (capNum == currentChapter && selectedBook.id == currentBookId) WarmGoldPrimary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable { onSelect(selectedBook.id, capNum) }
                                .padding(2.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Text(
                                    text = "$capNum",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Fechar") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReaderSettingsBottomSheet(
    currentFontSize: Int,
    currentTheme: ReaderColorMode,
    onFontSizeChange: (Int) -> Unit,
    onThemeChange: (ReaderColorMode) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Ajustes de Leitura", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(14.dp))

            Text("Tamanho da Fonte: ${currentFontSize}sp")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = { onFontSizeChange(currentFontSize - 2) }) { Text("A -") }
                Slider(
                    value = currentFontSize.toFloat(),
                    onValueChange = { onFontSizeChange(it.toInt()) },
                    valueRange = 14f..28f,
                    steps = 6,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(onClick = { onFontSizeChange(currentFontSize + 2) }) { Text("A +") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Tema de Fundo")
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                ReaderColorMode.values().forEach { mode ->
                    FilterChip(
                        selected = currentTheme == mode,
                        onClick = { onThemeChange(mode) },
                        label = { Text(mode.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RoyalBluePrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private fun parseHexColor(hexString: String): Color {
    return try {
        val clean = hexString.removePrefix("#")
        val colorInt = clean.toLong(16)
        if (clean.length == 6) {
            Color(0xFF000000 or colorInt)
        } else {
            Color(colorInt)
        }
    } catch (_: Exception) {
        Color(0xFFFFF176)
    }
}
