package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChurchEvent
import com.example.data.model.PastorMessage
import com.example.data.model.Verse
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val dailyVerse = viewModel.dailyVerse
    val pastorMessages by viewModel.churchRepository.pastorMessages.collectAsState()
    val dailyDevotional by viewModel.churchRepository.dailyDevotional.collectAsState()
    val events by viewModel.churchRepository.churchEvents.collectAsState()

    var showMessageDialog by remember { mutableStateOf<PastorMessage?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Banner Nobre de Boas-Vindas
        item {
            ChurchHeroHeader()
        }

        // Versículo do Dia (Card Destaque Ouro/Azul)
        item {
            DailyVerseCard(
                verse = dailyVerse,
                onShare = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\"${dailyVerse.text}\"\n— ${dailyVerse.reference} (Bíblia ACF)\nIgreja Batista Nova Aliança"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartilhar Versículo"))
                },
                onCopy = {
                    clipboardManager.setText(AnnotatedString("\"${dailyVerse.text}\" — ${dailyVerse.reference}"))
                    viewModel.showFeedback("Versículo copiado com sucesso!")
                },
                onReadInBible = {
                    viewModel.selectBookAndChapter(dailyVerse.bookId, dailyVerse.chapter)
                    viewModel.navigateTo(AppDestination.BIBLE)
                }
            )
        }

        // Atalhos de Ação Rápida
        item {
            QuickShortcutsGrid(
                onNavigate = { dest -> viewModel.navigateTo(dest) }
            )
        }

        // Mensagem Pastoral do Pr. Nelson Willame
        item {
            pastorMessages.firstOrNull()?.let { msg ->
                PastorMessageCard(
                    message = msg,
                    onClick = { showMessageDialog = msg }
                )
            }
        }

        // Devocional Diário
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("daily_devotional_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = WarmGoldDark
                        )
                        Text(
                            text = "Devocional do Dia",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dailyDevotional.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dailyDevotional.messageContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = WarmGoldContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Oração: ${dailyDevotional.prayerOfFaith}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Próximos Cultos e Eventos
        item {
            UpcomingEventsSection(
                events = events.take(3),
                onSeeAll = { viewModel.navigateTo(AppDestination.AGENDA) }
            )
        }
    }

    // Diálogo com a Mensagem Pastoral Completa
    if (showMessageDialog != null) {
        val msg = showMessageDialog!!
        AlertDialog(
            onDismissRequest = { showMessageDialog = null },
            title = {
                Text(
                    text = msg.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Text(
                            text = "${msg.pastorName} • ${msg.date}",
                            style = MaterialTheme.typography.labelSmall,
                            color = WarmGoldDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Texto Base: ${msg.scriptureReference}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = msg.fullText,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMessageDialog = null }) {
                    Text("Fechar")
                }
            }
        )
    }
}

@Composable
private fun ChurchHeroHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(RoyalBluePrimary, RoyalBlueLight)
                )
            )
            .padding(20.dp)
            .testTag("church_hero_header")
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = WarmGoldPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Church,
                            contentDescription = null,
                            tint = WarmGoldPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                Column {
                    Text(
                        text = "Igreja Batista Nova Aliança",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Comunidade da Fé e da Graça",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = WarmGoldPrimary
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "“Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna.”",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Composable
private fun DailyVerseCard(
    verse: Verse,
    onShare: () -> Unit,
    onCopy: () -> Unit,
    onReadInBible: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("daily_verse_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = null,
                        tint = WarmGoldDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "VERSÍCULO DO DIA",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = RoyalBluePrimary
                        )
                    )
                }
                Surface(
                    color = RoyalBluePrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "ACF",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "“${verse.text}”",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "— ${verse.reference}",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = WarmGoldDark
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCopy) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar Versículo",
                        tint = RoyalBluePrimary
                    )
                }
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartilhar",
                        tint = RoyalBluePrimary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                FilledTonalButton(
                    onClick = onReadInBible,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ler na Bíblia", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
private fun QuickShortcutsGrid(
    onNavigate: (AppDestination) -> Unit
) {
    val shortcuts = listOf(
        ShortcutItem("Bíblia ACF", Icons.Default.MenuBook, AppDestination.BIBLE),
        ShortcutItem("Hinários", Icons.Default.LibraryMusic, AppDestination.HYMNS),
        ShortcutItem("Mural Oração", Icons.Default.VolunteerActivism, AppDestination.PRAYER),
        ShortcutItem("Dízimos Pix", Icons.Default.QrCode, AppDestination.GIVING),
        ShortcutItem("Planos Leitura", Icons.Default.Checklist, AppDestination.PLANS),
        ShortcutItem("Quiz Bíblico", Icons.Default.Psychology, AppDestination.QUIZ)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Acesso Rápido",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = RoyalBluePrimary
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            shortcuts.take(3).forEach { item ->
                ShortcutButton(
                    item = item,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(item.destination) }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            shortcuts.drop(3).forEach { item ->
                ShortcutButton(
                    item = item,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(item.destination) }
                )
            }
        }
    }
}

private data class ShortcutItem(
    val title: String,
    val icon: ImageVector,
    val destination: AppDestination
)

@Composable
private fun ShortcutButton(
    item: ShortcutItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("shortcut_${item.destination.name.lowercase()}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = WarmGoldDark,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun PastorMessageCard(
    message: PastorMessage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("pastor_message_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = RoyalBluePrimary.copy(alpha = 0.06f)
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.RecordVoiceOver,
                    contentDescription = null,
                    tint = RoyalBluePrimary
                )
                Text(
                    text = "Palavra Pastoral",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${message.pastorName} • ${message.date}",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmGoldDark
                )
                Text(
                    text = "Ler Completa >",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            }
        }
    }
}

@Composable
private fun UpcomingEventsSection(
    events: List<ChurchEvent>,
    onSeeAll: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Próximos Cultos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalBluePrimary
            )
            TextButton(onClick = onSeeAll) {
                Text("Ver Agenda Completa")
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        events.forEach { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarmGoldContainer,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = event.dayOfWeek.take(3).uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = WarmGoldDark
                            )
                            Text(
                                text = event.time,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = event.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
