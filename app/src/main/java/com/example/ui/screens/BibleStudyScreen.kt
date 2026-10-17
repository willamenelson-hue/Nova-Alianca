package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BiblicalLocation
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppViewModel

@Composable
fun BibleStudyScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Mapas Bíblicos / Google Maps, 1 = Quiz Bíblico

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("bible_study_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = RoyalBluePrimary,
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Mapas Bíblicos (Google Maps)", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Quiz Bíblico", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
        }

        if (selectedTab == 0) {
            BiblicalMapsTab(
                locations = viewModel.churchRepository.biblicalLocations
            )
        } else {
            BiblicalQuizTab(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun BiblicalMapsTab(
    locations: List<BiblicalLocation>
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = WarmGoldContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        tint = WarmGoldDark,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Geografia Bíblica & Localização",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = WarmGoldDark
                        )
                        Text(
                            text = "Explore os marcos geográficos sagrados e abra diretamente no aplicativo do Google Maps.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        items(locations) { loc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("biblical_loc_${loc.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = loc.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                    }
                    Text(
                        text = "Localização Atual: ${loc.modernName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = WarmGoldDark,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = loc.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Significado Histórico: ${loc.historicalSignificance}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        loc.scriptureReferences.forEach { ref ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = ref,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${loc.latitude},${loc.longitude}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(mapIntent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Explore, contentDescription = null, tint = WarmGoldPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver no Google Maps")
                    }
                }
            }
        }
    }
}

@Composable
private fun BiblicalQuizTab(
    viewModel: AppViewModel
) {
    val quizState by viewModel.quizState.collectAsState()
    val questions = viewModel.churchRepository.quizQuestions

    if (quizState.isFinished) {
        // Tela de Fim do Quiz
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = WarmGoldPrimary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Parabéns!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Você concluiu o Quiz Bíblico da Nova Aliança!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Sua Pontuação: ${quizState.score} pontos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = WarmGoldDark
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.resetQuiz() },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Jogar Novamente")
                    }
                }
            }
        }
    } else {
        val currentQ = questions.getOrNull(quizState.currentQuestionIndex) ?: return

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pergunta ${quizState.currentQuestionIndex + 1} de ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = WarmGoldDark
                    )
                    Surface(
                        color = WarmGoldContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Pontos: ${quizState.score}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = WarmGoldDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Surface(
                            color = RoyalBluePrimary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Nível: ${currentQ.difficulty}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBluePrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = currentQ.question,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Alternativas
            items(currentQ.options.indices.toList()) { optIdx ->
                val optText = currentQ.options[optIdx]
                val isSelected = quizState.selectedOptionIndex == optIdx
                val isCorrect = optIdx == currentQ.correctIndex

                val cardBg = when {
                    quizState.isAnswerSubmitted && isCorrect -> Color(0xFFC8E6C9)
                    quizState.isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFFFCDD2)
                    isSelected -> WarmGoldContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !quizState.isAnswerSubmitted) {
                            viewModel.answerQuizQuestion(optIdx)
                        },
                    colors = CardDefaults.cardColors(containerColor = cardBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${'A' + optIdx}) ",
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                        Text(
                            text = optText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // Explicação Teológica e Referência Bíblica após resposta
            if (quizState.isAnswerSubmitted) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = RoyalBluePrimary.copy(alpha = 0.08f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Referência: ${currentQ.biblicalReference}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                color = RoyalBluePrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentQ.explanation,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                item {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Próxima Pergunta >")
                    }
                }
            }
        }
    }
}
