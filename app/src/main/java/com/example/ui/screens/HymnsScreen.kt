package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GospelSong
import com.example.data.model.Hymn
import com.example.data.model.HymnCollection
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppViewModel

@Composable
fun HymnsScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0 = Harpa Cristã, 1 = Cantor Cristão, 2 = Gospel / letras.mus.br
    var searchQuery by remember { mutableStateOf("") }

    var selectedHymnForViewing by remember { mutableStateOf<Hymn?>(null) }
    var selectedGospelForViewing by remember { mutableStateOf<GospelSong?>(null) }

    val harpaHymns = viewModel.churchRepository.searchHymns(HymnCollection.HARPA_CRISTA, searchQuery)
    val cantorHymns = viewModel.churchRepository.searchHymns(HymnCollection.CANTOR_CRISTAO, searchQuery)
    val gospelSongs = viewModel.churchRepository.searchGospelSongs(searchQuery)

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("hymns_screen")
    ) {
        // Abas dos Hinários
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = RoyalBluePrimary,
            contentColor = androidx.compose.ui.graphics.Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Harpa Cristã", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Cantor Cristão", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Letras Gospel", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
        }

        // Campo de Busca
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = if (selectedTab == 2) "Buscar louvor ou cantor..." else "Buscar por número ou título...",
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = WarmGoldDark)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Limpar")
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .testTag("hymn_search_input")
        )

        // Lista de Acordo com a Aba Selecionada
        when (selectedTab) {
            0 -> {
                HymnListView(
                    hymns = harpaHymns,
                    onHymnClick = { selectedHymnForViewing = it }
                )
            }
            1 -> {
                HymnListView(
                    hymns = cantorHymns,
                    onHymnClick = { selectedHymnForViewing = it }
                )
            }
            2 -> {
                GospelSongsListView(
                    songs = gospelSongs,
                    onSongClick = { selectedGospelForViewing = it },
                    onOpenLetras = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }

    // Modal de Visualização de Hino Tradicional
    if (selectedHymnForViewing != null) {
        val hymn = selectedHymnForViewing!!
        AlertDialog(
            onDismissRequest = { selectedHymnForViewing = null },
            title = {
                Column {
                    Text(
                        text = "Hino ${hymn.number} - ${hymn.title}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "${hymn.collection.displayName} • Autor: ${hymn.author}",
                        style = MaterialTheme.typography.labelSmall,
                        color = WarmGoldDark
                    )
                }
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Text(
                            text = hymn.lyrics,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 24.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedHymnForViewing = null }) {
                    Text("Fechar")
                }
            }
        )
    }

    // Modal de Visualização de Letra Gospel com Link letras.mus.br
    if (selectedGospelForViewing != null) {
        val song = selectedGospelForViewing!!
        AlertDialog(
            onDismissRequest = { selectedGospelForViewing = null },
            title = {
                Column {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "Intérprete: ${song.artist}",
                        style = MaterialTheme.typography.labelSmall,
                        color = WarmGoldDark
                    )
                }
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Text(
                            text = song.fullLyrics,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.lyricsUrl))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Abrir no letras.mus.br")
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedGospelForViewing = null }) {
                    Text("Fechar")
                }
            }
        )
    }
}

@Composable
private fun HymnListView(
    hymns: List<Hymn>,
    onHymnClick: (Hymn) -> Unit
) {
    if (hymns.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nenhum hino encontrado com o critério pesquisado.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hymns) { hymn ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onHymnClick(hymn) }
                        .testTag("hymn_item_${hymn.number}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarmGoldContainer,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${hymn.number}",
                                    fontWeight = FontWeight.Bold,
                                    color = WarmGoldDark,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = hymn.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Autor: ${hymn.author}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Abrir",
                            tint = WarmGoldDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GospelSongsListView(
    songs: List<GospelSong>,
    onSongClick: (GospelSong) -> Unit,
    onOpenLetras: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(songs) { song ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSongClick(song) }
                    .testTag("gospel_song_${song.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = song.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBluePrimary
                            )
                            Text(
                                text = song.artist,
                                style = MaterialTheme.typography.bodySmall,
                                color = WarmGoldDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        IconButton(onClick = { onOpenLetras(song.lyricsUrl) }) {
                            Icon(
                                imageVector = Icons.Default.Launch,
                                contentDescription = "Ver no letras.mus.br",
                                tint = WarmGoldDark
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = song.lyricsSnippet,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
